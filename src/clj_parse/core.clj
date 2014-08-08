(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(defn get-from-html
  "returns a lazy sequence containing the content of tag(s) elem in file f"
  [f elem]
  (map html/text
       (html/select
        (html/html-resource (io/file f))
        [elem])))
