(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(defn get-from-html
  "returns a lazy sequence containing the content of tag(s) elem in file f"
  [res elem]
  (map html/text
       (html/select
        res
        [elem])))

(defn get-files
  ""
  []
  (filter #(not (.isDirectory %))
          (file-seq (clojure.java.io/file "."))))

(defn main
  ""
  []
  (filter #(not-empty %)
          (map #(get-from-html (html/html-resource %) :h1)
               (get-files))))
