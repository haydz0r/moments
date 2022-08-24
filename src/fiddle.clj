(ns fiddle
     (:require [clojure.string :as str]))

(->> [{:color "white"}
      {:color "red"}
      {:count 1}]
     (filter :color)
     (mapv :color))

(-> "abc"
    (str/replace "a" "z"))

#_#_ (sc.api/spy) 
(use 'sc.api)
;https://github.com/vvvvalvalval/scope-capture
