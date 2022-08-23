(ns moments.core
  (:require [java-time :as jt])
  (:require [clojure.pprint :refer [pprint]]))

(def moments
  (read-string (slurp "data/moments.edn")))

(def todays-date (str (jt/local-date)))

(defn time-between [todays-date moment-date]
  (jt/time-between (jt/local-date todays-date) (jt/local-date moment-date) :days))

(defn moment-period [todays-date moment-date]
  (jt/period (jt/local-date todays-date) (jt/local-date moment-date)))

(defn add-tense [time-period formatted-time-period]
  (if (.isNegative time-period)
    (str formatted-time-period " ago")
    (str "in " formatted-time-period)))

(defn today? [moment-date todays-date]
  (= (jt/local-date moment-date) (jt/local-date todays-date)))

(defn full-format
  "Formats a time period into a date that includes years, months and days"
  [time-period]
    (add-tense time-period (str (abs (.getYears time-period)) " years "
                                (abs (.getMonths time-period)) " months "
                                (abs (.getDays time-period)) " days")))

(defn auto-format
  "Formats a date that will only include a time period (years, months and days) if the time period has a value"
  [time-period]
  (cond 
    (and (= (abs (.getYears time-period)) 0) (= (abs (.getMonths time-period)) 0)) (str (abs (.getDays time-period)) " days")
    (and (= (abs (.getYears time-period)) 0) (> (abs (.getMonths time-period)) 0)) (str (abs (.getMonths time-period)) " months " (abs (.getDays time-period)) " days")
    :else (full-format time-period)))

(defn format-duration-dispatch [moment todays-date] 
  (cond 
    (today? (:date moment) todays-date) :today
    :else (:format moment)))

(defmulti format-duration format-duration-dispatch)

(defmethod format-duration :full
 [moment todays-date]
 (full-format (moment-period todays-date (:date moment))))

(defmethod format-duration :auto
 [moment todays-date]
 (auto-format (moment-period todays-date (:date moment))))

(defmethod format-duration :today
  [_moment _todays-date]
  "today!")

(defn enrich-moments [moments todays-date]
  (mapv #(assoc % 
                :duration-in-days (time-between todays-date (:date %))
                :duration-formatted (format-duration % todays-date)) moments))

(defn -main
  [] 
  (pprint (enrich-moments moments todays-date)))

