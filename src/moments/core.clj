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
          :duration-formatted (f/format-duration moment todays-date)))

(defn process-moments [moments todays-date]
  (mapv #(enrich-moments % todays-date) moments))

(defn main'
  "pure driver for logic"
  []
  (let [moments (load-moments-from-file "data/moments.edn") 
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
