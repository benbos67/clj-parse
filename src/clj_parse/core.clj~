(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]
            [clojure.string :refer [upper-case]]
            [pantomime.mime :refer [mime-type-of]])
  (:gen-class :main true))

(html/deftemplate t1 "clj_parse/template.html" [keyword return-type description params]
  [:KeyWord] (html/set-attr :name keyword :retval return-type)
  [:Overload] (html/set-attr :descr description))

(defn get-html-files
  "returns a sequence of html files from directory dir, recursive"
  [dir]
  (filter #(contains?
            #{"application/xhtml+xml" "text/html"}
            (mime-type-of %))
          (file-seq (io/file dir))))

(defn -main
  ""
  [& args]
  (doseq [res (map #(html/html-resource %) (get-html-files (first args)))
          :when (.contains
                 (upper-case (str (html/text (first (html/select res [:h6]))) ""))
                 "RETURN")]
    (println (str (html/text (first (html/select res [:h2])))
                  (map #(html/text %) (butlast (html/select res [:tr])))))))

