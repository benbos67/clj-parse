(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(defn parse-html
  ""
  [file elem]
  (with-open [rdr (io/reader file)]
    (map html/text
         (html/select (html/html-resource rdr) [elem]))))

(parse-html "/home/ben/test.txt" :h1)

(map html/text
     (html/select (html/html-resource (io/file "/home/ben/test.txt")) [:h1]))
