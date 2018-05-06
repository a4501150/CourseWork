(ns deque.t-core
  (:use midje.sweet)
  (:use [deque.core])
  (:import [deque.core Deque] ))

(facts "about this lab"
  (fact "This stdent is awesome."
        (+ 10 7)  => 17))

(facts "About push front and push back."
       (fact "push front works when supposed to."
             (push-front (make-deque) 1) => (Deque. '(1) '() 1)
             (push-front (push-front (make-deque) 1) 2) => (Deque. '(2 1) '() 2))
       (fact "push back works when supposed to."
             (push-back (make-deque) 1) => (Deque. '() '(1) 1)
             (push-back (push-back (make-deque) 1) 2) => (Deque. '() '(2 1) 2))
       )

(facts "About flip front and flip back."
       (fact "flip front works when it should."
             (flip-front (push-back (push-back (make-deque) 1)2))=> (Deque. '(1 2) '() 2))
       (fact "flip front does nothing when it should."
             (flip-front (make-deque)) => (make-deque)
             (flip-front (push-front (make-deque) 1))=> (push-front (make-deque) 1))
       (fact "flip back works when it should."
             (flip-back (push-front (push-front (make-deque) 1)2))=> (Deque. '() '(1 2) 2))
       (fact "flip back does nothing when it should."
             (flip-back (make-deque))=>(make-deque)
             (flip-back (push-back (make-deque) 1))=> (push-back (make-deque) 1)))

(facts "About front and back."
       (fact "front returns the correct answer and flips if neccesary."
             (front (push-front (push-front (make-deque) 1) 2)) => 2
             (front (push-back (push-back (make-deque) 1) 2)) => 1)
       (fact "back returns the correct answer and flips if neccesary."
             (back (push-front (push-front (make-deque) 1) 2)) => 1
             (back (push-back (push-back (make-deque) 1) 2)) => 2)
       )

(facts "About pop front and pop back."
       (fact "pop front works on non empty deques."
             (pop-front (push-front (push-front (make-deque) 1)2))=> (push-front (make-deque) 1)
             (pop-front (push-front (make-deque) 1))=> (make-deque))
       (fact "pop back works on non empty deques."
             (pop-back (push-back (push-back (make-deque) 1)2))=> (push-back (make-deque) 1)
             (pop-back (push-back (make-deque) 1))=> (make-deque))
       (fact "pop front works when a flip is required"
             (pop-front (push-back (push-back (make-deque) 1)2))=> (push-front (make-deque) 2)
             (pop-front (push-back (make-deque) 1))=> (make-deque))
       (fact "pop back works when a flip is required"
             (pop-back (push-front (push-front (make-deque) 1)2))=> (push-back (make-deque) 2)
             (pop-back (push-front (make-deque) 1))=> (make-deque))
       (fact "pop front works on an empty deque."
             (pop-front (make-deque)) => (make-deque))
       (fact "pop back works on an empty deque."
             (pop-back (make-deque)) => (make-deque))
       )
