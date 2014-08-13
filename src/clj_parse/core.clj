(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io])
  (:gen-class :main true))

(defn get-from-html
  "returns a lazy sequence containing the content of tag(s) elem in file f"
  [res elem]
  (map html/text
       (html/select
        res
        [elem])))

(defn get-files
  ""
  [dir]
  (filter #(not (.isDirectory %))
          (file-seq (clojure.java.io/file dir))))

(defn -main
  ""
  [& args]
  (for [res (map #(html/html-resource %) (get-files (first args)))
        :when (.contains (str (first (get-from-html res :h1)) "") "Clojure")]
    (first (get-from-html res :h1))))

(defn -main1
  [& args]
  (doseq [res (map #(html/html-resource %) (get-files (first args)))]
    (println res)))
