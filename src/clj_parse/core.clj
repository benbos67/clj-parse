(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]
            [clojure.string :refer [upper-case]]
            [pantomime.mime :refer [mime-type-of]])
  (:gen-class :main true))

(defn get-from-html
  "returns a lazy sequence containing the content of tag(s) elem in file f"
  [res elem]
  (map html/text
       (html/select res [elem])))

(defn get-html-files
  "returns a sequence of file objects from directory dir"
  [dir]
  (filter #(or
            (= (mime-type-of %) "application/xhtml+xml")
            (= (mime-type-of %) "text/html"))
          (file-seq (clojure.java.io/file dir))))

(defn -main
  ""
  [& args]
  (doseq [res (map #(html/html-resource %) (get-html-files (first args)))
        :when (.contains (upper-case (str (first (get-from-html res :h6)) "")) "RETURN")]
    (println (first (get-from-html res :h6)))))

