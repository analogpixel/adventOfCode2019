(ns day-1.core
  (:require [clojure.string :as str]))

; calculate the fuel
(defn calc-fuel [f]
  (- (Math/floor (/ f 3)) 2) 
  )

; recursive figure out fuel
(defn calc-fuel2 [f]
  (loop [t 0
         fuel f]
        (def tmp (calc-fuel fuel))
        (if (> tmp 0) (recur (+ tmp t) tmp) t)
       ) 
)

(defn -main []

  ;; load in the data to a list of numbers
  (def data (map #(Integer/parseInt %1) (str/split (slurp "./data/data.txt") #"\n")))

  ;; solution for part one.
  (def solution (map #(calc-fuel %1) data))
  (def part1 (reduce + solution))
  (println part1)

  ;; solution for part two
  (def solution (map #(calc-fuel2 %1) data))
  (def part2 (reduce + solution))
  (println part2)
  )
