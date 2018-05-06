;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.
 (ns linked_list_lab.core)

;; We start with a defrecord.  In traditional lisps, the
;; first element of a list is called the "car", standing for
;; "Contents of Address Register," the name of the register where
;; it was stored.  The pointer to the rest of the list was called
;; the "cdr," for "Contents of Decrement Register", and is pronounced
;; "could-er".

(defrecord Cons [car cdr])

;; One problem with singly linked lists is that finding the length is
;; expensive $${\cal O}(n)$$.  We can use a wrapper class to deal with
;; that.  Here we have a `List` record that keeps a pointer to the list
;; along with the size.

(defrecord List [data size])

;; The `make-list` function just creates an empty list.


(defn make-list
  "Create an empty list."
  []
  (List. nil 0))


;; The `insert-front` function shows a special syntax Clojure has.
;; If you know the argument to your function should be a record or a
;; hash-map, you can use this pattern-matching syntax as a shorthand.
;; Here we have `{:keys [data size]}`, which will create new variables
;; `data` and `size`.  So if I pass in `{:data 10 :size 20}`, then
;; `data` will be given 10, and `size` will be given 20.
;; This is often called "destructuring".

;; Test broke-1 will not increment the size.
;; Test broke-2 will forget to do the cons.
;; Test broke-3 will replace the cons and not point to the next one.

(defn insert-front
  "Insert an element at the beginning of the list."
  [{:keys [data size]} new-elt]
  (List. (Cons. new-elt data) (+ 1 size)))

;;(insert-front nulist 6)
;; Here are some utility functions that convert Clojure lists to
;; our Cons. record, and vice-versa.  The broke versions will not
;; mess with these.

(defn list-to-cons
  [xx]
  (cond (empty? xx) nil
        :else       (Cons. (first xx) (list-to-cons (next xx)))))

(defn cons-to-list
  [xx]
  (cond (nil? xx)   '()
        :else       (cons (:car xx) (cons-to-list (:cdr xx)))))

;; The `insert-sorted` function assumes that the elements are orderable
;; and puts the element in the spot that will preserve the ordering.

;; Test broke-4 will use `(Cons. elt (:cdr xx))` in the third case.
;; Test broke-5 will use `(Cons. (:car xx) nil)` in the second case.

(defn insert-ordered-cons
  "Insert the element `elt` into an ordered `Cons.` chain.
This is used by `insert-ordered`."
  [elt xx]
  (cond (empty? xx) (Cons. elt nil)
        (> elt (:car xx)) (Cons. (:car xx) (insert-ordered-cons elt (:cdr xx)))
        :fine-be-that-way (Cons. elt xx)))



(defn insert-ordered
  "Insert an element into an ordered list."
  [{:keys [data size]} new-elt]
  (List. (insert-ordered-cons new-elt data) (+ size 1)))


;; The `delete` function will delete one element from the list.

;; Test broke-6 will truncate the list past the deletion point.
;; Test broke-7 will forget to decrement the size.
;; Test broke-8 will always decrement the size, even if the element is not found.


(defn delete-cons
  "Delete `elt` from an `Cons.` chain.
    This is used by `delete` "
  [elt xx]
  (cond (empty? xx) nil
        (= elt (:car xx)) (:cdr xx)
        :else (Cons. (:car xx) (delete-cons elt (:cdr xx)))))




(defn delete-count
  "Count the size after delete"
  [xx]
 (cond (empty? xx) 0
       :else (+ 1 (delete-count (:cdr xx)))))

;;(delete-count x)


(defn delete
  "Delete `elt` from `xx` "
  [new-elt {:keys [data size]}]
  (List. (delete-cons new-elt data) (delete-count (delete-cons new-elt data))))

(def nulist (List. (Cons. 1 (Cons. 5 (Cons. 5 nil))) 3))
(delete 5 nulist)

;; The `delete-all` function will delete all copies of elt from xx.


;; Test broke-9 will delete only one copy.
;; Test broke-10 will decrement the count instead of properly subtracting the
;;      number of deletions.
(defn delete-all-cons
  "Delete all  `elt` from an `Cons.` chain.
    This is used by `delete` "
  [elt xx]
  (cond (empty? xx) nil
        (= elt (:car xx)) (delete-all-cons elt (:cdr xx))
        :else (Cons. (:car xx) (delete-all-cons elt (:cdr xx)))))

(defn delete-all
  "Delete all occurrences of `elt` from `xx`."
  [ new-elt {:keys [data size]}]
  (List. (delete-all-cons new-elt data) (delete-count (delete-all-cons new-elt data))))

;;(delete-all 5 nulist)

;;(-> (insert-front (make-list) 10) :data :car)
