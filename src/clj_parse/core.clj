(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(defn get-from-html
  "returns a lazy sequence containing the content of tag(s) elem in file f"
  [res elem]
  (let [res (html/html-resource (io/file f))]
    (map html/text
         (html/select
          res
          [elem]))))

(defn parse-help-files
  [dir]
  (doseq dir
    (
;for each file f
;get-from-html-1 (html/html-resource (io/file f)) :h6
     ))
