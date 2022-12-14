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
         :duration-date-to-today (c/time-between todays-date (:date moment))
         :period (jt/as-map (c/moment-period todays-date (:date moment)))
         :formatted-date (jt/format "dd MMM YYYY" (jt/local-date (:date moment)))
         :formatted-period (f/format-duration todays-date moment)))

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

  #_#_(defn handler [request]
        {:status 200
         :headers {"Content-Type" "text/plain"}
         :body "Hello Momenters!"})
    (raj/run-jetty handler {:port 3000}) ; playing around with ring. requires [ring.adapter.jetty :as raj]

  (jt/format "dd MMM YYYY" (jt/local-date "2022-01-01")))
