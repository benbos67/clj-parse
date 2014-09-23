(ns clj-parse.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]
            [clojure.string :refer [upper-case]]
            [pantomime.mime :refer [mime-type-of]])
  (:gen-class :main true))

(html/deftemplate auto-complete-xml "clj_parse/auto-complete-xml.html"
  [keyword return-type description params]
  [:KeyWord] (html/set-attr :name keyword))

(html/defsnippet keyword "clj_parse/keyword.html"
  [:KeyWord]
  [keyword params]
  [:KeyWord] (html/set-attr :name keyword)
  [:Overload] (html/set-attr :retval return-type :descr description))

(html/defsnippet param "clj_parse/param.html"
  [:KeyWord]
  [keyword params]
  [:KeyWord] (html/set-attr :name keyword)
  [:Overload] (html/set-attr :retval return-type :descr description))

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

