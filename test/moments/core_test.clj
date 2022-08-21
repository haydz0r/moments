(ns moments.core-test
  (:require [clojure.test :refer :all]
            [moments.core :refer :all])
   (:require [java-time :as jt]))

(deftest time-between-test 
  (testing "Tests whether the difference of two dates are returned as a duration"
    (is (= -31 (moments.core/time-between "2022-09-01" "2022-08-01")))
    (is (= 31 (moments.core/time-between "2022-08-01" "2022-09-01")))
    (is (= 0 (moments.core/time-between "2022-09-01" "2022-09-01")))))

(deftest test-moment-enriching
  (testing "Tests whether moments data is enriched with calculated data such as the duration"
    (let [moments-test-data
         [{:title "Test Date 1" :date "2009-08-14"} 
          {:title "Test Date 2" :date "2050-08-01"}]]
    
      (is (= 
           [{:title "Test Date 1" :date "2009-08-14" :duration-in-days -4523 :duration-auto-formatted "12 years 4 months 18 days ago"}
           {:title "Test Date 2" :date "2050-08-01" :duration-in-days 10439 :duration-auto-formatted "in 28 years 7 months 0 days"}]
           (moments.core/enrich-moments moments-test-data "2022-01-01"))))))

(deftest test-to-show-steve 
  (testing "Why does read-string return the data type PersistentList for the date compared to the original type of LocalDate?"
    (is (= 
         (type (:date (read-string (slurp "data/moments-test.edn")))) 
         (type (:date {:title "Test Date" :date (jt/local-date 2009 8 14)}))))))

(deftest test-time-period-formatting 
  (testing "When given a time period, test that it returns the correct human readable breakdown into years/months/days" ;
    (let [past-time-period (jt/period (jt/local-date "2022-03-21") (jt/local-date "2020-08-15"))]
      (is (= 
           "1 years 7 months 6 days ago"
           (auto-format past-time-period)))) ; https://www.timeanddate.com/date/durationresult.html?d1=15&m1=08&y1=2020&d2=21&m2=03&y2=2022
    (let [future-time-period (jt/period (jt/local-date "2022-03-21") (jt/local-date "2023-08-15"))]
      (is (= 
           "in 1 years 4 months 25 days"
           (auto-format future-time-period))))
    (let [today-time-period (jt/period (jt/local-date "2023-01-01") (jt/local-date "2023-01-01"))]
      (is (= 
           "today"
           (auto-format today-time-period)))))) ; https://www.timeanddate.com/date/durationresult.html?d1=15&m1=8&y1=2023&d2=21&m2=3&y2=2022

(deftest test-time-period-is-today
  (testing "Given a time period, check whether the period is the current date (true) or not (false)"
    (let [today-time-period (jt/period (jt/local-date "2023-01-01") (jt/local-date "2023-01-01"))]
      (is (= 
           true
           (today? today-time-period))))
    (let [not-today-time-period (jt/period (jt/local-date "2023-01-02") (jt/local-date "2023-01-01"))]
      (is (=
           false
           (today? not-today-time-period))))))