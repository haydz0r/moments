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

(defn add-tense [time-period, formatted-time-period]
  (if (.isNegative time-period)
    (str formatted-time-period " ago")
    (str "in " formatted-time-period)))

(defn today? [time-period]
  (and (= (.getYears time-period) 0)
       (= (.getMonths time-period) 0)
       (= (.getDays time-period) 0)))

(defn full-format
  "Formats a time period into a date that includes years, months and days"
  [time-period]
  (if (today? time-period)
    "today"
    (add-tense time-period (str (abs (.getYears time-period)) " years "
                                (abs (.getMonths time-period)) " months "
                                (abs (.getDays time-period)) " days"))))

(defn auto-format
  "Automatically formats a date that will only include a time period (years, months and days) if the time period has a value"
  [moment]
  "to be implemented...")

(defmulti format-duration :format)

(defmethod format-duration :full
 [moment]
 (full-format (moment-period todays-date (:date moment))))

(defmethod format-duration :auto
 [moment]
 (auto-format (moment-period todays-date (:date moment))))

(defn enrich-moments [moments todays-date]
  (mapv #(assoc % 
                :duration-in-days (time-between todays-date (:date %))
                :duration-formatted (format-duration %)) moments))

(defn -main
  [] 
  (pprint (enrich-moments moments todays-date)))

