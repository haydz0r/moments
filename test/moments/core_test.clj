(ns moments.core-test
  (:require [clojure.test :refer :all]
            [moments.core :refer :all])
   (:require [java-time :as jt]))

(deftest time-between-test 
  (testing "Tests whether the difference of two dates are returned as a duration"
    (is (= (moments.core/time-between "2022-09-01" "2022-08-01") -31))
    (is (= (moments.core/time-between "2022-08-01" "2022-09-01") 31))
    (is (= (moments.core/time-between "2022-09-01" "2022-09-01") 0))))

(deftest enrich-moments-test
  (testing "Tests whether moments data is enriched with calculated data such as the duration"
    (let [moments-test-data
         [{:title "Test Date 1" :date "2009-08-14"} 
          {:title "Test Date 2" :date "2050-08-01"}]]
    
      (is (= (moments.core/enrich-moments moments-test-data "2022-01-01") 
             [{:title "Test Date 1" :date "2009-08-14" :duration-in-days -4523}
              {:title "Test Date 2" :date "2050-08-01" :duration-in-days 10439}])))))

(deftest test-to-show-steve 
  (testing "Why does read-string return the data type PersistentList for the date compared to the original type of LocalDate?"
    (is (= 
         (type (:date (read-string (slurp "data/moments-test.edn")))) 
         (type (:date {:title "Test Date" :date (jt/local-date 2009 8 14)}))))))