(ns day-4.core)

;; convert a number to a list of digits
(defn num-to-list [x]
  (loop [n x 
         nlist [] ]
    (if (< n 10)
      (cons  n nlist )
      (recur (quot n 10) (cons (mod n 10) nlist) )
      )
    )
  )

;; part 2 checker
(defn check-num2 [x]
  (loop [prevnum (first x)
         nextnum (first (rest x))
         runcount (if (= prevnum nextnum) 1 0)
         nums (rest (rest x))]
    ; (println prevnum nextnum runcount nums)
    (if (or (empty? nums) (and (= runcount 1) (not= nextnum (first nums))))
      (= runcount 1)
      (recur nextnum (first nums) (if (= nextnum (first nums)) (+ 1 runcount) 0 ) (rest nums))
      )
    )
  )
;; part 1 checker
(defn check-num [x]
  (loop [prevnum (first x)
         nextnum (first (rest x) )
         nums (rest (rest x))
         d (= prevnum nextnum)
         ] 

    (if (< nextnum prevnum)
      false
      (if (empty? nums) 
        (or d (= nextnum prevnum))
        (recur nextnum (first nums) (rest nums) (or d (= prevnum nextnum)))
        )
      )
    )
  )

(defn -main []
  (def bottom 165432)
  (def top 707912)
  ;; 1716 correct answer 1163 (part 2)
  (def part1 (filter #(check-num (num-to-list %1)) (range bottom top)))
  (def part2 (filter #(check-num2 (num-to-list %1)) part1))

  (println (count part1))
  (println (count part2))
  )
