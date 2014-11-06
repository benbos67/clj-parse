(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]
            [clojure.string :refer [upper-case]]
            [pantomime.mime :refer [mime-type-of]])
  (:gen-class :main true))

(html/deftemplate auto-complete-xml "auto-complete-xml.html"
  [keyword return-type description params]
  [:KeyWord] (html/set-attr :name keyword))

(html/defsnippet keyword-snippet "keyword.html"
  {[:KeyWord] [[:Param]]}
  [{keyword :keyword return-type :return-type description :description val :val}]
  [:KeyWord] (html/set-attr :name keyword)
  [:KeyWord :Overload] (html/do->
                        (html/set-attr :retVal return-type)
                        (html/set-attr :descr description))
  [:Keyword :Overload :Param] (html/set-attr :name val))

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

