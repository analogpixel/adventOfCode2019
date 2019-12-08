(ns day-5.core-test
  (:require [clojure.test :refer :all]
            [day-5.core :refer :all]))


(def data1-test [1002 4 3 4 33])
(def data2-test [102 4 3 5 99 0])
(def data3-test [1102 4 3 5 99 0])

(deftest test-get-data-1 (testing "get-data-1" (is (= (get-data data2-test 0 1) 4 )))) 
(deftest test-get-data-1 (testing "get-data-1" (is (= (get-data data2-test 0 2) 5 )))) 

(deftest test-compute-1 (testing "compute-1" (is (= (compute data1-test 1) [1002 4 3 4 99] )))) 
(deftest test-compute-2 (testing "compute-2" (is (= (compute data2-test 1) [102 4 3 5 99 20] )))) 
(deftest test-compute-3 (testing "compute-3" (is (= (compute data3-test 1) [1102 4 3 5 99 12] )))) 
