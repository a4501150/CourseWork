(ns heap-lab.core)

;; # Array Based Heaps
;;
;; Just in time for thanksgiving, a simple lab about Heaps!
;;
;; We will use vectors to handle this, with a top-level record
;; to keep track of the vector and the size.

(defrecord Heap [size data])

;; We will initialize this using the `make-heap` function.

(defn make-heap
  "Creates an empty heap.  Specify the size for the data vector.
The vector will be populated with `nil`."
  [size]
  (Heap. size (apply vector (repeat size nil))))

;; To access the elements of the heap, we will use these functions
;; `get`, `left`, `right`, and `parent`.

(defn heap-get
  "Return the value of the heap vector at the given index.
Throws an exception if the index is out of the range.
this is part of the implementation, not for public consumption."
  [heap loc]
  (cond (>= loc (count (:data heap)))
        (throw (Exception. (str "Get called with " loc " but last vector slot is " (dec (count (:data heap))))))

        :otherwise
        (get-in heap [:data loc])))

(defn heap-set
  "Set the value of the heap vector at the given index.
Throws an exception if the index is out of the range.
this is part of the implementation, not for public consumption."
  [heap loc value]
  (cond (>= loc (count (:data heap)))
        (throw (Exception. (str "Get called with " loc " but last vector slot is " (dec (count (:data heap))))))

        :otherwise
        (assoc-in heap [:data loc] value)))

(defn heap-left
  "Return the left index."
  [loc]
  (inc (* loc 2)))

(defn heap-right
  "Return the right index."
  [loc]
  (+ 2 (* loc 2)))

(defn heap-parent
  "Return the parent index."
  [loc]
  (int (/ (dec loc) 2)))

;; Now it's time for your code!  You need these three, but you are welcome to
;; write helper functions if you want (e.g., `percolate-down`.)  Do **not** write
;; `midje` tests for them, because they are not part of the spec.

(defn top
  "Return the top element of a heap.
If the heap has no elements, return `nil`."
  [heap])

(defn delete
  "Deletes the first element of the heap.
Returns the new heap."
  [heap]
  nil)

(defn add
  "Adds a new element to the heap.
If the data vector is too small, we resize it."
  [heap data]
  nil)

