(ns moments.core
  (:require [java-time :as jt]
            [clojure.pprint :refer [pprint]]
            [moments.format :as f]
            [moments.calc :as c]))

(defn load-moments-from-file [dir]
  (-> (slurp dir)
  (read-string)))

(defn enrich-moments [moment todays-date]
  (assoc moment
         :duration-in-days (c/time-between todays-date (:date moment)) 
         :period (jt/as-map (c/moment-period todays-date (:date moment)))
         :period-formatted (f/format-duration todays-date moment)))

(defn process-moments [moments todays-date]
  (mapv #(enrich-moments % todays-date) moments))

(defn main'
  "pure driver for logic"
  []
  (let [moments (load-moments-from-file "resources/moments.edn") 
        todays-date (str (jt/local-date))]
    (process-moments moments todays-date)))

(defn -main
  "CLI entry point with pprint side-effect to stdout"
  []
  (pprint (main')))

(comment

  (do (main') nil)
  (-main)

  )
