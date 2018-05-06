(ns heap-lab.t-core
  (:use midje.sweet)
  (:use [heap-lab.core]))

(facts "about numbers"
       (fact "one plus one is two."
             (+ 1 1)  =>  2)
       (fact "two plus one is three."
             (+ 2 1)  =>  3))
