(ns deque.core)

(defrecord Deque [front back size])

;; # Your Work

(defn make-deque
  "Create an empty deque."
  ([]
  (Deque. '() '() 0))
  ([l]
   (Deque. l '() (count l)))
  ([front back]
   (Deque. front back (+ (count front)(count back))))
  ([front back size]
   (Deque. front back size)))

(defn deque-size
  "Return the size of a deque."
  [dq]
  (:size dq))

(defn push-front
  "Adds an element to the front of the deque."
  [dq elt]
  (let [{:keys [front back size]} dq]
    (Deque. (cons elt front) back (inc size))))

(defn push-back
  "Adds an element to the back fo the deque."
  [dq elt]
  (let [{:keys [front back size]} dq]
    (Deque. front (cons elt back) (inc size)))
 )

(defn flip-front
  "Flip the back list to the front list, if necessary."
  [dq]
  (let [{:keys [front back size]} dq]
    (if (empty? front) (Deque. (reverse back) '() size)
      dq)
))

(defn flip-back
  "Flip the front list to the back list, if necessary."
  [dq]
  (let [{:keys [front back size]} dq]
    (if (empty? back) (Deque. '() (reverse front)  size)
      dq)))

(defn front
  "Return the front element of the deque.  May cause a flip."
  [dq]
  (-> dq flip-front :front first)
)

(defn back
  "Return the back element of the deque.  May cause a flip."
  [dq]
   (-> dq flip-back :back first)
)

(defn pop-front
  "Pops/dequeues an element from the front of the deque."
  [dq]
  (let [{:keys [front back size]} (flip-front dq)]
    (Deque. (rest front) back (if (> size 0) (dec size) size)))
)

(defn pop-back
  "Pops/dequeues an element from the back of the deque."
  [dq]
  (let [{:keys [front back size]} (flip-back dq)]
    (Deque.  front (rest back) (if (> size 0) (dec size) size)))
)

