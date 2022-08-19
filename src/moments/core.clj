(ns moments.core 
  (:gen-class)
  (:require [java-time :as jt])
  (:require [clojure.pprint :as pp]))

(def moment-dates 
  [{:title "Started Dating Bri" :date (jt/local-date 2009 8 14)} 
   {:title "Started Working" :date (jt/local-date 2013 8 1)}
   {:title "Married Bri" :date (jt/local-date 2014 1 25)}
   {:title "Bought First Home" :date (jt/local-date 2014 11 14)}
   {:title "Addi Born" :date (jt/local-date 2019 3 2)}
   {:title "Demi Born" :date (jt/local-date 2021 10 1)}
   {:title "Bought Second Home" :date (jt/local-date 2022 9 15)}
   ])

(def now (jt/local-date)) ; get todays date

(defn calculate-moment-dates [current-date moment-dates]
  (map #(assoc % :duration (jt/time-between current-date (get % :date) :days)) moment-dates))

(defn -main
  []
  (pp/pprint (calculate-moment-dates now moment-dates)))

