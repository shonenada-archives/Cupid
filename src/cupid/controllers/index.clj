(ns cupid.controllers.index
  (:use [cupid.utils.resp])
  (:require [selmer.parser :refer [render-file]]))

(defn index [request]
  (response (render-file "templates/index.html")))
