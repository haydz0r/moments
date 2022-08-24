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
; scope-capture repo: https://github.com/vvvvalvalval/scope-capture

; java-time repo: https://github.com/dm3/clojure.java-time

; clj-kondo repo: https://github.com/clj-kondo/clj-kondo/blob/master/doc/linters.md#misplaced-docstring 