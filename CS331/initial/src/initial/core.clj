(ns initial.core)

(defn plus
 "Adds up numbers."
  [& xx]
  (apply + xx))




(defn socialist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it taxes the result by subtracting
  (n-2).  If n<2 it adds one as a subsidy."
  [& xx]

  (let [n (count xx)]

    (cond (< n 2) (apply + (cons 1 xx))

          (= n 2) (apply + xx)

          (> n 2) (apply + (cons (- 2 n) xx))


    )))

 ;(socialist-plus 10 20 30)
 ; (socialist-plus 10 20 30)
 ; (socialist-plus 10 20 20 10)

(defn capitalist-plus
  "Adds up numbers [x_1, x_2, ... x_n].  If n>2, it subsidizes the result by adding
  (n-2).  If n<2 it subtracts one as a tax."

  [& xx]
  (let [n (count xx)]
    (cond (< n 2) (apply + (cons -1 xx))

          (= n 2) (apply + xx)

          (> n 2) (apply + (cons (- n 2) xx))

     )))

;(capitalist-plus 10)
 ;(capitalist-plus 10 20 30)

(defn communist-plus
  "Adds up numbers.  To allow for equality the sum is always 10."
  ; I think you'll nail this one.
  [& xx] 10
 )

;(communist-plus)

(defn political-extreemist-plus
  "Adds up numbers like a political extreemist, i.e., by multiplying them.
  You get to pick which political extreemists this refers to."
  [& xx] (apply * xx)

  ; All you have to do is remember which symbol means to multiply....
)

;(political-extreemist-plus 10 10 10 10 10 10)


