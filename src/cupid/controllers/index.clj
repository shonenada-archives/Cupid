(ns cupid.controllers.index
  (:use [ring.util.response])
  (:require [selmer.parser :refer [render-file]]))

(defn index [request]
  (let [resp-text (render-file "index.html" {})]
    (-> (response resp-text))))
