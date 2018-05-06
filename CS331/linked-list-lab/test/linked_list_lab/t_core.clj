(ns linked_list_lab.t-core
  (:use midje.sweet)
  (:use [linked_list_lab.core])
  (:import [linked_list_lab.core Cons List]))

(facts "about `Cons.`"
  (fact "it creates a record that has a `car` and a `cdr`."
    (:car (Cons. 1 2)) => 1
    (:cdr (Cons. 1 2)) => 2))

(facts "about `List.`"
  (fact "it creates a record with a `data` and a `size`."
    (:data (List. 10 20))  => 10
    (:size (List. 10 20))  => 20)
  (fact "you use `make-list` to create an empty one."
    (let [nulist (make-list)]
      (:data nulist) => nil
      (:size nulist) => 0)))

(facts "about `insert-front`"
  (let [nulist1 (insert-front (make-list) 10)
        nulist2 (insert-front nulist1 20)]
    (fact "it increments the size properly."
      (:size nulist1) => 1
      (:size nulist2) => 2)
    (fact "it puts the elements in the correct order."
      (-> nulist1 :data :car) => 10
      (-> nulist1 :data :cdr) => nil
      (-> nulist2 :data :car) => 20
      (-> nulist2 :data :cdr :car) => 10)))

(facts "about `list-to-cons`"
  (fact "it converts lists properly"
    (list-to-cons nil) => nil
    (list-to-cons '(3)) => (Cons. 3 nil)
    (list-to-cons '(4 2)) => (Cons. 4 (Cons. 2 nil))
    (list-to-cons '(6 2 7))  => (Cons. 6 (Cons. 2 (Cons. 7 nil)))))

(facts "about `cons-to-list`"
  (fact "it converts lists properly"
    (cons-to-list nil) => '()
    (cons-to-list (Cons. 3 nil)) => '(3)
    (cons-to-list (Cons. 4 (Cons. 2 nil))) => '(4 2)
    (cons-to-list (Cons. 6 (Cons. 2 (Cons. 7 nil))))  => '(6 2 7)))

(facts "about `insert-ordered`"
  (let [nulist3 (List. (Cons. 1 (Cons. 5 (Cons. 8 nil))) 3)]
    (fact "it places elements properly."
      (-> (insert-ordered nulist3 0) :data) => (list-to-cons '(0 1 5 8))
      (-> (insert-ordered nulist3 2) :data) => (list-to-cons '(1 2 5 8))
      (-> (insert-ordered nulist3 7) :data) => (list-to-cons '(1 5 7 8))
      (-> (insert-ordered nulist3 9) :data) => (list-to-cons '(1 5 8 9))
      (-> (insert-ordered nulist3 5) :data) => (list-to-cons '(1 5 5 8)))
    (fact "it handles size correctly."
      (-> (insert-ordered nulist3 0) :size) => 4
      (-> (insert-ordered nulist3 2) :size) => 4
      (-> (insert-ordered nulist3 7) :size) => 4
      (-> (insert-ordered nulist3 9) :size) => 4
      (-> (insert-ordered nulist3 5) :size) => 4
         )
    ))

(facts "about `delete`"
  (let [nulist4 (List. (Cons. 1 (Cons. 5 (Cons. 8 (Cons. 5 (Cons. 1 nil))))) 5)]
    (fact "it removes elements properly."
      (-> (delete 1 nulist4) :data) => (list-to-cons '(5 8 5 1))
      (-> (delete 5 nulist4) :data) => (list-to-cons '(1 8 5 1))
      (-> (delete 8 nulist4) :data) => (list-to-cons '(1 5 5 1))
      (-> (delete 9 nulist4) :data) => (list-to-cons '(1 5 8 5 1)))

    (fact "it handles size correctly."
      (-> (delete 0 nulist4) :size) => 5
      (-> (delete 1 nulist4) :size) => 4
      (-> (delete 5 nulist4) :size) => 4
      (-> (delete 8 nulist4) :size) => 4
      (-> (delete 9 nulist4) :size) => 5 )))

(facts "about `delete-all` "
  (let [nulist5 (List. (Cons. 1 (Cons. 5 (Cons. 8 (Cons. 5 (Cons. 1 nil))))) 5 )]
    (fact "it removes elements properly."
        (-> (delete-all 0 nulist5) :data) => (list-to-cons '(1 5 8 5 1))
        (-> (delete-all 1 nulist5) :data) => (list-to-cons '(5 8 5))
        (-> (delete-all 5 nulist5) :data) => (list-to-cons '(1 8 1))
        (-> (delete-all 8 nulist5) :data) => (list-to-cons '(1 5 5 1))
        (-> (delete-all 9 nulist5) :data) => (list-to-cons '(1 5 8 5 1)))
    (fact "it handles size correctly."
        (-> (delete-all 0 nulist5) :size) => 5
        (-> (delete-all 1 nulist5) :size) => 3
        (-> (delete-all 5 nulist5) :size) => 3
        (-> (delete-all 8 nulist5) :size) => 4
        (-> (delete-all 9 nulist5) :size) => 5
          )))


(facts "about this lab"
  (fact "the student never started.  Fix this `facts` from."
    (+ 10 20) => 30))
