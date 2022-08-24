(ns moments.calc
  (:require [java-time :as jt]))

(defn time-between [todays-date moment-date]
  (jt/time-between (jt/local-date todays-date) (jt/local-date moment-date) :days))

(defn moment-period [todays-date moment-date]
  (jt/period (jt/local-date todays-date) (jt/local-date moment-date)))

(defn today? [todays-date moment-date]
  (= (jt/local-date moment-date) (jt/local-date todays-date)))


(comment 
  (let [test-period (jt/period (jt/local-date "2022-08-05") (jt/local-date "2009-03-22"))]
    (jt/as-map test-period)))