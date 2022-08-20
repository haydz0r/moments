(ns moments.core
  (:require [java-time :as jt])
  (:require [clojure.pprint :refer [pprint]]))

(defn time-between [todays-date moment-date]
  (jt/time-between (jt/local-date todays-date) (jt/local-date moment-date) :days))

(defn enrich-moments [moments todays-date]
  (mapv #(assoc % :duration-in-days (time-between todays-date (:date %))) moments))

(def moments
  (read-string (slurp "data/moments.edn"))) ; not a pure function? how to structure where this goes?

(def todays-date (str (jt/local-date))) ;get todays date. not a pure function? how to structure where this goes?

(defn -main
  [] 
  (pprint (enrich-moments moments todays-date)))

