(ns traversal_lab.core)

;; This algorithm is pretty standard.  The version used here is
;; described at http://mazeworks.com/mazegen/mazetut/index.htm
(declare find-solution)
;; ## A note about abstraction

;; It's possible to build abstractions without objects, and even without
;; records!  You have to be more careful though; objects and records
;; give the language tools to help you enforce your abstractions.  But
;; you don't use those, you can use simple functions to implement your
;; model---just be sure that nobody uses the data apart from your
;; functions.

;; # The data structure
;;
;; We will use integers to encode our maze nodes, and place these into a vector
;; of vectors.

;; ## Walls
;;
;; Walls are set using the four low order bits of the integer.

(def empty-node 0)

(defn get-wall "Given a node and a direction, check if there is a wall."
  [node dir]
  (case dir
    :w (bit-test node 3)
    :s (bit-test node 2)
    :e (bit-test node 1)
    :n (bit-test node 0)))

(defn set-wall "Given a node and a direction, build or remove a wall."
  [node dir up]
  (let [op (if up bit-set bit-clear)]
    (case dir
      :w (op node 3)
      :s (op node 2)
      :e (op node 1)
      :n (op node 0))))

(defn walls-up "Raise all four walls."
  [node]
  (bit-or node 15))

;; ## Borders
;;
;; Borders are encoded using bits 4 to 7.
;; They are used to encode the outer border of the maze.

(defn get-border "Given a node and a direction, check if there is a border."
  [node dir]
  (case dir
    :w (bit-test node 7)
    :s (bit-test node 6)
    :e (bit-test node 5)
    :n (bit-test node 4)))

(defn set-border "Given a node and a direction, set the border status."
  [node dir up]
  (let [op (if up bit-set bit-clear)]
    (case dir
      :w (op node 7)
      :s (op node 6)
      :e (op node 5)
      :n (op node 4))))

;; ## Solution path
;;
;; The solution path is encoded using bits 8 to 11.
;; This encodes the direction you need to go to solve the maze.
;; The student code will make use of this.

(defn get-solution "Given a node and a direction, check if it is part of the solution."
  [node dir]
  (case dir
    :w (bit-test node 11)
    :s (bit-test node 10)
    :e (bit-test node 9)
    :n (bit-test node 8)))

(defn set-solution "Given a node and a direction, set the solution status."
  [node dir up]
  (let [op (if up bit-set bit-clear)]
    (case dir
      :w (op node 11)
      :s (op node 10)
      :e (op node 9)
      :n (op node 8))))

;; # 2-D Array manipulation functions
;;
;; These functions make it more convenient for us to manipulate the arrays.

(defn swap-2d
  "Take 2-D vector `v` with coordinates `x` and `y` and swap the corresponding value using function `f`."
  ( [v x y f]
      (let [content ((v x) y)
            row (v x)]
        (assoc v x (assoc row y (f content)))))
  ( [v [r c] f]
      (swap-2d v r c f)))

(defn reset-2d
  "Take 2-D vector `v` with coordinates `x` and `y` and reset the corresponding value using `content`."
  [v x y content]
  (let [row (v x)]
    (assoc v x (assoc row y content))))


(defn get-2d
  "Return the content of 2-D vector `v` at `x` and `y`."
  ( [v x y]
      ((v x) y))
  ( [v [r c]]
      (get-2d v r c)))

;; # Direction functions

(defn get-direction
  "Return the direction to get from a to b."
  [[row-a col-a] [row-b col-b]]
  (cond (= row-a row-b)
        (cond (= (inc col-a) col-b) :e
              (= (dec col-a) col-b) :w
              :else :undefined)
        (= col-a col-b)
        (cond (= (inc row-a) row-b) :s
              (= (dec row-a) row-b) :n
              :else :undefined)
        :else :undefined))

;; This doesn't need to be a function, a hash-map will do.

(def reverse-direction
  {:n :s
   :s :n
   :e :w
   :w :e})

;; # Maze Functions
;;
;; The maze is a vector of row vectors.  So, the first dimension is the row,
;; and the second is the column.  Location `[0,0]` is the north west corner.

(defn all-walls-up?
  "Check if all the walls of cell `[r c]` in maze `m` are up."
  [m [r c]]
  (let [node (get-2d m r c)]
    (and (get-wall node :n)
         (get-wall node :s)
         (get-wall node :e)
         (get-wall node :w))))

(defn get-neighbors
  "Get the neighbors of the given node.  Uses border markings for the boundary."
  [m [r c]]
  (let [node (get-2d m r c)]
    (filter identity
            (list (when-not (get-border node :n) [(dec r) c])
                  (when-not (get-border node :s) [(inc r) c])
                  (when-not (get-border node :e) [r (inc c)])
                  (when-not (get-border node :w) [r (dec c)])))))

(defn get-walled-neighbors
  "Get the neighbors of `loc` and return the ones that have all four walls up."
  [m loc]
  (filter #(all-walls-up? m %) (get-neighbors m loc)))

(defn set-ew-borders
  "Set the east and west borders on the maze."
  [m-orig rows cols]
  (loop [m m-orig
         r 0]
    (if (< r rows)
      (recur (-> m (swap-2d r 0 #(set-border % :w true))
                 (swap-2d r (dec cols) #(set-border % :e true)))
             (inc r))
      m)))

(defn set-ns-borders
  "Set the north and south borders of he maze."
  [m-orig rows cols]
  (loop [m m-orig
         c 0]
    (if (< c cols)
      (recur (-> m (swap-2d 0 c #(set-border % :n true))
                 (swap-2d (dec rows) c #(set-border % :s true)))
             (inc c))
      m)))

(defn set-walls
  "Raise the walls on all nodes of the maze."
  [m-orig rows cols]
  (loop [m m-orig
         r 0
         c 0]
    (cond (>= r rows) m
          (>= c cols) (recur m (inc r) 0)
          :else (recur (swap-2d m r c walls-up)
                       r (inc c)))))

(defn make-maze-paths
  "Use a DFS algorithm to create the paths in the maze.  The algorithm is
pretty standard.  This function is tail recursive (i.e., uses a loop construct),
so maze size will not be an issue."

  [m-orig rows cols]
  (loop [m m-orig
         stk '()
         remain (dec (* rows cols))
         c-loc [0 0]]
    (if (zero? remain) m
        (let [candidates (get-walled-neighbors m c-loc)]
          (if (empty? candidates)
            (recur m
                   (rest stk)
                   remain
                   (first stk))
            ;; Select candidate and build wall
            (let [next-loc (nth candidates (rand-int (count candidates) ))
                  dir (get-direction c-loc next-loc)]
              (recur (-> m (swap-2d (c-loc 0) (c-loc 1) #(set-wall % dir false))
                         (swap-2d (next-loc 0) (next-loc 1) #(set-wall % (reverse-direction dir) false)))
                     (cons c-loc stk)
                     (dec remain)
                     next-loc)))))))

(defn generate-maze
  "This is the main function to generate a maze."
  [rows cols]
  (let [empty-maze (vec (repeat rows (vec (repeat cols empty-node))))]
    (-> empty-maze
        (set-ew-borders rows cols)
        (set-ns-borders rows cols)
        (set-walls rows cols)
        (make-maze-paths rows cols))))

;; # Maze Printing

(defn print-north-wall [the-row]
  (loop [c 0]
    (print "+")
    (if (< c (count the-row))
      (do
        (if (get-wall (the-row c) :n)
          (print "-")
          (print " "))
        (recur (inc c))))))

(defn print-south-wall [the-row]
  (loop [c 0]
    (print "+")
    (if (< c (count the-row))
      (do
        (if (get-wall (the-row c) :s)
          (print "-")
          (print " "))
        (recur (inc c))))))

(defn print-row [the-row]
  (loop [c 0]
    (if (< c (count the-row))
      (do
        (if (get-wall (the-row c) :w)
          (print "|")
          (print " "))
        (if (or (get-solution (the-row c) :n)
                (get-solution (the-row c) :s)
                (get-solution (the-row c) :e)
                (get-solution (the-row c) :w))
          (print "x")
          (print " "))
        (recur (inc c)))
      (print "|"))))

(defn show-maze
  "Generate an ASCII art representation of a maze."
  [m]
  (loop [row 0]
    (let [the-row (m row)]
      (print-north-wall the-row)
      (println)
      (print-row the-row)
      (println)
      (if (< (inc row) (count m))
        (recur (inc row))
        (print-south-wall the-row)))))


;; ### Sample Run
;; <pre><code>
;; traversal_lab.core> (show-maze (generate-maze 10 10))
;; +-+-+-+-+-+-+-+-+-+-+
;; | |             |   |
;; + +-+-+-+ +-+-+ +-+ +
;; | |       |   | |   |
;; + + +-+-+-+-+ + + + +
;; | | |       | |   | |
;; + + + +-+ + + +-+-+ +
;; | | | | | |       | |
;; + + + + + +-+-+-+-+ +
;; |   | | |         | |
;; +-+-+ + +-+-+-+-+ + +
;; |   |           |   |
;; + + +-+-+-+-+-+ +-+-+
;; | |   |     |   |   |
;; + +-+-+ + +-+ +-+-+ +
;; | |   | |     |   | |
;; + + + + +-+-+-+ + + +
;; | | | |         |   |
;; + + + +-+-+-+-+-+-+ +
;; |   |               |
;; +-+-+-+-+-+-+-+-+-+-+
;; </code></pre>


;; # Student Code!!
;;
;; You only need to write two functions here.  They
;;might be tricky though.  Especially the second one.  Feel free to add
;;your own functions, but do not test them in the unit tests or else you
;;will throw off the grading script.
(declare get-aval-neighbors)
(declare get-pos-moves)

(defn solve-maze-dfs
  "Solves a maze using the DFS algorithm.  It should set the `solution` bits of the maze
to show the path, and return the corresponding maze."
  [maze start-loc goal-loc]
  (find-solution  maze start-loc goal-loc))

(defn solve-maze-bfs
  "Solves the maze using BSF.  In this version, there are multiple goals.  The solution
should be for the goal that is closest.  Returns the solved maze."
  [maze start-loc & goals]
  (find-solution  maze start-loc (first goals))) 
;;Helper functions
;;-----------------------------------------------------------------------------------------------
(defn node-in-direction
  "Return the coordinates from a location and a direction."
  [[row col] dir]
  (case dir
    :n [(dec row) col]
    :s [(inc row) col]
    :e [row (inc col)]
    :w [row (dec col)]))


(defn get-aval-neighbors "returns all neighbors that you can get to from your current location"
  [maze cnode]
  (loop [avn '()
         neighbors (get-neighbors maze cnode)]
    (if (empty? neighbors)
      avn
      (if (get-wall (get-2d maze cnode) (get-direction cnode (first neighbors)))
        (recur avn (rest neighbors) )
        (recur (cons (first neighbors) avn ) (rest neighbors) )))))

(defn get-pos-moves  "returns all possible future move locations"
  [maze [r c]]
  (loop [pos-neigh (get-aval-neighbors maze [r c])
         pos-next-locs '()]
    (if
     (empty? pos-neigh) pos-next-locs
     ;;here is the bug
     (let [dir-to-nnode (get-direction [r c]  (first pos-neigh))]
       (if (get-solution (get-2d maze (first pos-neigh)) (reverse-direction dir-to-nnode))
         
         (recur (rest pos-neigh) pos-next-locs)
         (recur (rest pos-neigh) (cons (first pos-neigh) pos-next-locs)))))))



(defn find-solution "if the node is the value of the goal location return the maze with the solution bits set.
 If at a dead end return nil. else return this function applied to all surounding avalible nodes"
  [maze cnode gnode]
  (if (= cnode gnode) maze
      (loop [paths (get-pos-moves maze cnode)]
        (if (empty? paths) nil
            (let [solution (find-solution (swap-2d maze cnode #(set-solution % (get-direction cnode (first paths)) true)) (first paths) gnode)]
              (if (nil? solution) (recur (rest paths))
                  solution))
            ))))

  ;;-----------------------------------------------------------------------------------------------
  ;; Example call:
  ;; (solve-maze maze [0 0] [50, 234] [90 34] [88 2])
  ;; goals = '([50 234] [90 34] [88 2])


  ;; ## Extra things
  ;;Returning a string intead of Printing
  

(defn str-north-wall [the-row]
  (loop [c 0
         wstr []]
    (if (< c (count the-row))

      (if (get-wall (the-row c) :n)
        (recur (inc c) (conj (conj wstr "+") "-"))
        (recur (inc c) (conj (conj wstr "+") " ")))
      (apply str (conj wstr "+"))
      )))

(defn str-south-wall "Returns a string that represents the south wall"
  [the-row]
  (loop [c 0
         wstr []]
    (if (< c (count the-row))
          (recur (inc c) (conj (conj wstr "+") "-"))
          (apply str (conj wstr "+"))
      )))

(defn str-row "returns a string that represents the row"
  [the-row]
  (loop [c 0
         wstr []]
    (if (< c (count the-row))

        (if (get-wall (the-row c) :w)
          (recur (inc c) (conj (conj wstr "|") " "))
          (recur (inc c) (conj (conj wstr " ") " ")))
        (apply str (conj wstr "|"))
      )))


(defn maze-str "Returns a string that represents the maze"
  [m]
  (apply str (loop [row 0
                    wstr []]
               (let [the-row (m row)]
                 (if (< (inc row) (count m))
                   (recur (inc row) (conj wstr (str-north-wall the-row) "\n" (str-row the-row) "\n"))
                   (conj wstr (str-north-wall the-row) "\n" (str-row the-row) "\n" (str-south-wall the-row) "\n")

                   )))))


;;(print (maze-str (generate-maze 10 10)))

;;(println)
;;(show-maze (find-solution (generate-maze 10 10) [0 0] [9 9]))






;;(def temp (generate-maze 10 10))

;;(get-2d temp 0 0)
;;(swap-2d temp 0 0 #(set-solution % :w true))
;;(reset-2d temp 0 0)
;; Finished the lab early?  Here are things you could do....
;;
;; 1. Modify the `show-maze` function to print out the solution path.
;; 1. Try making mazes with different boudaries by setting the border differently.
;; 1. You may have to modify `show-maze` again to have it print properly.
;; 1. Use the ASCII line characters to make the output prettier.  How will you handle
;;    corners?


;; (loop [n 100
;;        i 0
;;        a 1N
;;        b 0N]
;;   (if (= n i)
;;       a
;;       (recur n (inc i) (+ a b) a )))



