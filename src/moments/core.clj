(ns moments.core
  (:require [java-time :as jt])
  (:require [clojure.pprint :refer [pprint]]))

(def moments 
  (read-string (slurp "data/moments.edn")))

(def todays-date (jt/local-date)) ;get todays date

(defn time-between [todays-date moment-date]
  (jt/time-between todays-date (jt/local-date moment-date) :days))

(defn enrich-moments [moments todays-date]
  (map #(assoc % :duration-in-days (time-between todays-date (:date %))) moments))

(defn -main
  []
  (pprint (enrich-moments moments todays-date)))

