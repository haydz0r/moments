(ns moments.core
  (:require [java-time :as jt])
  (:require [clojure.pprint :refer [pprint]]))

(defn add-tense [time-period, formatted-time-period]
  (if (.isNegative time-period)
    (str formatted-time-period " ago")
    (str "in " formatted-time-period)))

(defn today? [time-period]
  (and (= (.getYears time-period) 0)
       (= (.getMonths time-period) 0)
       (= (.getDays time-period) 0)))

(defn auto-format [time-period]
  "Automatically formats a time period into a years/months/days ago meant to be read by a human"
  (if (today? time-period)
    "today"
    (add-tense time-period (str (abs (.getYears time-period)) " years "
                                (abs (.getMonths time-period)) " months "
                                (abs (.getDays time-period)) " days"))))

(defn time-between [todays-date moment-date]
  (jt/time-between (jt/local-date todays-date) (jt/local-date moment-date) :days))

(defn moment-period [todays-date moment-date]
  (jt/period (jt/local-date todays-date) (jt/local-date moment-date)))

(defn enrich-moments [moments todays-date]
  (mapv #(assoc % 
                :duration-in-days (time-between todays-date (:date %))
                :duration-auto-formatted (auto-format (moment-period todays-date (:date %)))) moments))

(def moments
  (read-string (slurp "data/moments.edn"))) ; not a pure function? how to structure where this goes?

(def todays-date (str (jt/local-date))) ;get todays date. not a pure function? how to structure where this goes?

(defn -main
  [] 
  (pprint (enrich-moments moments todays-date)))

