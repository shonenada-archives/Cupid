(ns cupid.routes
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [cupid.controllers.index :as index-controller]))

(defn page-not-found []
  "Page not found.")

(defroutes app-routes
  (GET "/" [request] index-controller/index)
  (route/not-found (page-not-found)))
