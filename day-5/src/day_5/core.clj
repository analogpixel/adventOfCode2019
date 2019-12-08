(ns day-5.core
  (:require [clojure.string :as str])
  (:gen-class))


;; make sure instruction set is 4 long
(defn pad [l]
  (reverse (take 5 (reverse (concat [0 0 0 0 0] l))))
  )

;; convert a number to a list
(defn num-to-list [x]
  (into [] (pad
             (loop [n x
                    nlist [] ]
               (if (< n 10)
                 (cons  n nlist )
                 (recur (quot n 10) (cons (mod n 10) nlist) )
                 )
               )
             )
        )
  )

;; get the last number 
(defn parse-cmd [c] 
  (last (num-to-list c))
  )

(defn get-data [data iptr loc]
  (let [
        [p3 p2 p1 op2 op1] (num-to-list (nth data iptr))  
        op (+ op1 (* op2 10) )
        param [p1 p2 p3]
        ]

    (if (= 0 (param (- loc 1)))
      ;; use the register 
      (nth data (nth data (+ iptr loc)))
      ;; just use the value there
      (nth data (+ iptr loc))
      )
    )
  )

(defn print-cmd [d iptr]
(case (mod (d iptr) 100)
  1 (println "add:" (d iptr) (get-data d iptr 1) "+" (get-data d iptr 2) "put in:" (d (+ 3 iptr)))
  2 (println "mult:" (d iptr) (get-data d iptr 1) "*" (get-data d iptr 2) "put in:" (d (+ 3 iptr)))
  3 (println "input:" (d iptr))
  4 (println "output:" (d iptr))
  5 (println "Jump if true:" (d iptr) (get-data d iptr 1)  (get-data d iptr 2) "put in:" (d (+ 3 iptr)))
  6 (println "Jump if false:" (d iptr) (get-data d iptr 1) (get-data d iptr 2) "put in:" (d (+ 3 iptr)))
  7 (println "if lessthan store 1" (d iptr) (get-data d iptr 1) "<" (get-data d iptr 2) "put 1/0 in:" (d (+ 3 iptr)))
  8 (println "if equal store 1"(d iptr) (get-data d iptr 1) "=" (get-data d iptr 2) "put 1/0 in:" (d (+ 3 iptr)))
  99 (println "quit")
  (println "default" (d iptr))
  )
)

(defn compute [data input]
  (println "--- start compute --")
  (loop [iptr 0
         d data]

    (print-cmd d iptr)

    (if (= (mod (nth d iptr) 100) 4) (println "output" (get-data d iptr 1)))

    (if (= (mod (nth d iptr) 100) 99)
      d ;; return the data 
      (recur 
              ;; increase iptr 
              (case (mod (nth d iptr) 100) 
               1 (+ iptr 4)
               2 (+ iptr 4)
               3 (+ iptr 2)
               4 (+ iptr 2)
               5 (if (not= (get-data d iptr 1) 0) (get-data  d iptr 2) (+ 3 iptr))
               6 (if (= (get-data d iptr 1) 0) (get-data  d iptr 2) (+ 3 iptr))
               7 (+ iptr 4)
               8 (+ iptr 4)
               )

             (case (mod (nth d iptr) 100) ; get the last 2 digits of the number
               1 (assoc d (d (+ iptr 3)) (+ (get-data d iptr 1) (get-data d iptr 2)))
               2 (assoc d (d (+ iptr 3)) (* (get-data d iptr 1) (get-data d iptr 2)))
               3 (assoc d (d (+ iptr 1)) input) ;; input
               4 d ;; output
               5 d
               6 d
               7 (assoc d (d (+ iptr 3)) (if (< (get-data d iptr 1) (get-data d iptr 2)) 1 0 ))
               8 (assoc d (d (+ iptr 3)) (if (= (get-data d iptr 1) (get-data d iptr 2)) 1 0 ))
               )

             )
      )

    )
  )

(defn -main [& args]

  (def data (into []  (map #(Integer/parseInt %) (str/split (clojure.string/trim (slurp "./data/data.txt")) #","))))

  ;; 223 too low
  (compute data 5)
  )
