(ns moments.core-test
  (:require [clojure.test :refer :all]
            [moments.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest positive-duration
  (testing "When a moment occurs in the future, we should receive a positive number")
  (def moments [{:title "Test Moment" :date "2022-09-01"}])
  (def todays-date "2022-08-01")
  (def duration (enrich-moments moments date))
  (is (= duration 30)))