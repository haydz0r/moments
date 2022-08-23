(ns moments.format
  (:require [moments.calc :as c]
            [clojure.string :refer [trim]]))

(defn add-tense [time-period formatted-time-period]
  (if (.isNegative time-period)
    (str formatted-time-period " ago")
    (str "in " formatted-time-period)))

(defn get-units-of-time [time-period]
  {"year" (.getYears time-period)
   "month" (.getMonths time-period)
   "day" (.getDays time-period)})

(defn auto-format 
  "Formats a date that will only include a time period (years, months and days) if the time period has a value"
  [format units-of-time-for-period]
  (->> (reduce (fn [accumulator unit-mapping]
            (let [absolute-duration (abs (second unit-mapping))
                  unit-of-time (first unit-mapping)]
              (cond
                (and (zero? absolute-duration) (= format :auto)) accumulator
                (= 1 absolute-duration) (conj accumulator (str absolute-duration " " unit-of-time " "))
                :else (conj accumulator (str absolute-duration " " unit-of-time "s ")))))
          []
          units-of-time-for-period)
      (apply str)
      (trim)))

(defn format-duration-dispatch [moment todays-date]
  (cond
    (c/today? (:date moment) todays-date) :today
    :else :auto-or-full))

(defmulti format-duration format-duration-dispatch)

(defmethod format-duration :auto-or-full
  [moment todays-date]
  ;(full-format (c/moment-period todays-date (:date moment))))
  (let [moment-period (c/moment-period todays-date (:date moment))]
    (->> (get-units-of-time moment-period)
         (auto-format (:format moment))
         (add-tense moment-period))))

(defmethod format-duration :today
  [_moment _todays-date]
  "today!")