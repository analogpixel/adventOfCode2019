(ns day-3.core
  (:require [clojure.string :as str]) 
  (:require [clojure.set])
  )

(defn count-steps [datain x y]

  ;; loop over all the commands and figure out every where it goes
  (loop [d datain
         p [0 0]
         cmd (first (first d))
         amt (last (first d))
         cnt 0
         ]
    
    ;; (println p)

    (if (or (= p [x y]) (= (count d) 0))
      ;; return the points 
      (if (= (count d) 0) -1 cnt)

      ;; calculate the next set of points
      (recur 
        ;; if amt is 0, go to the next command  
        (if (= amt 1) (rest d) d)

        ;; update the points 
        (let [x (first p)
              y (last p)]
        (case cmd
          "U" [x (- y 1)]
          "D" [x (+ y 1)]
          "R" [(+ x 1) y]
          "L" [(- x 1) y]
          )
        )

        ;; update cmd if needed
        (if (= amt 1) (first (first (rest d))) cmd)

        ;; update amt 
        (if (= amt 1) (last (first (rest d)))  (- amt 1)) 

        ;; update the count
        (+ cnt 1)
        )
      ) 
    )
  )

(defn create-points [datain]

  ;; loop over all the commands and figure out every where it goes
  (loop [d datain
         p [0 0] 
         pdata []
         ]

    (if (= (count d) 0)
      ;; return the points 
      pdata

      ;; calculate the next set of points
      (let [inst (first d)
            cmd (first inst)
            amt (last inst)
            x (first p)
            y (last p)]

        (recur (rest d) 
               (case cmd
                 "U" [x (- y amt)]
                 "D" [x (+ y amt)]
                 "R" [(+ x amt) y]
                 "L" [(- x amt) y]
                 )

               (concat pdata 
                       (case cmd
                         "U" (map #(vector x (+ y %1) ) (range (* -1 amt) 0))
                         "D" (map #(vector x (+ y %1) ) (range 0 (+ 1 amt)))
                         "R" (map #(vector (+ x %1) y ) (range 0 (+ 1 amt)))
                         "L" (map #(vector (+ x %1) y ) (range (* -1 amt) 0))
                         )
                       ) 
               )
        ) 
      )
    )
  )


(defn -main [& args]

  ;; load the data
  (def data  (str/split (str/trim (slurp "./data/data.txt")) #"\n"))
  (def data1 (map #(vector (subs %1 0 1) (Integer/parseInt (subs %1 1))) (str/split (nth data 0) #",")))  
  (def data2 (map #(vector (subs %1 0 1) (Integer/parseInt (subs %1 1))) (str/split (nth data 1) #",")))  

  (def ss
    (clojure.set/intersection (set (create-points data1)) (set (create-points data2)))
    )

  (println ss)
  (println  (map #(+ (Math/abs (first %1)) (Math/abs (last %1))) ss))

  (println
    (sort
  (map #(+ %1 %2)
    (map #(count-steps data1 (first %1) (last %1)) ss)
    (map #(count-steps data2 (first %1) (last %1)) ss)
    )
  )
  )
  )

