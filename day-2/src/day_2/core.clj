(ns day-2.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn solver [x y data]

  (loop [iptr 0
         d (assoc (assoc data 1 x) 2 y)] 

    (if (not= (nth d iptr) 99) 
      (let [a1 (nth d (+ iptr 1))
            a2 (nth d (+ iptr 2))
            a3 (nth d (+ iptr 3))
            ]

        (recur (+ iptr 4) (case (nth d iptr)
                            1  (assoc d a3 (+ (nth d a1) (nth d a2)))
                            2  (assoc d a3 (* (nth d a1) (nth d a2)))
                            ))
        )

      ;; else
      d
      )
    )
  )

(defn -main [& args]

  (def data (into []  (map #(Integer/parseInt %) (str/split (clojure.string/trim (slurp "./data/data.txt")) #","))))

  ;; Part 1
  (println (solver 12 2 data))

  ;; Part 2
  ;; plot out a few values to see if you can figure out what the program is doing
  ;; once you figure ou the pattern for the first digit and second digit, you have the answer
  ;; (println (for [x (range 0 99)]
  ;;   [x (nth (solver 41 x data) 0)]
  ;; ))
)
