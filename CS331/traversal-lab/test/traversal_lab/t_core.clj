(ns traversal_lab.t-core
  (:use midje.sweet)
  (:use [traversal_lab.core]))

(facts "about the student"
  (fact "they never got started."
        (+ 100 20 3)  => 123))

(defn num-solution-paths [maze loc]
  (count (filter #(get-solution (get-2d maze (loc 0) (loc 1)) %) [:n :s :e :w])))

(defn get-solution-dir [maze loc]
  (first (filter #(get-solution (get-2d maze (loc 0) (loc 1)) %) [:n :s :e :w])))

(defn solution-hits-wall [maze loc dir]
  (let [node (get-2d maze (loc 0) (loc 1))]
    (and (get-solution node dir)
         (get-wall node dir))))

(defn go-direction
  "Return the coordinates from a location and a direction."
  [[row col] dir]
  (case dir
    :n [(dec row) col]
    :s [(inc row) col]
    :e [row (inc col)]
    :w [row (dec col)]))

(defn maze-is-solved [maze start goal seen]
  (cond (= start goal) "solved"
        (seen start)   "solution loops"
        (> 1 (num-solution-paths maze start)) "too many solution paths"
        :else (let [dir (get-solution-dir maze start)]
                (cond
                 (solution-hits-wall maze start dir) "solution goes through wall"
                 :else (maze-is-solved maze (go-direction start dir) goal (conj seen start))))))

(facts "about dfs solver"
       (let [unsolved-maze (generate-maze 10 10)
             solved-maze (solve-maze-dfs unsolved-maze [0 0] [9 9])]
         (fact "it works."
               (maze-is-solved solved-maze [0 0] [9 9] #{}) => "solved"
               (maze-is-solved (solve-maze-dfs (generate-maze 15 20) [0 0] [10 13]) [0 0] [10 13] #{})=> "solved"
               (let [start-loc (get-2d solved-maze 0 0)]
                 (or (get-solution start-loc :e)
                     (get-solution start-loc :s)
                     (get-solution start-loc :s)
                     (get-solution start-loc :e))) => true
               (let [end-loc (get-2d solved-maze 9 9)]
                 (or (get-solution end-loc :n)
                     (get-solution end-loc :w)
                     (get-solution end-loc :n)
                     (get-solution end-loc :w))) => false
               )))


