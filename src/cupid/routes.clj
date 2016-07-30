(ns cupid.routes
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [cupid.controllers.index :as index-controller]
            [cupid.controllers.account :as account-contorller]))

(defn page-not-found []
  "Page not found.")

(defroutes app-routes
  (GET "/" [request] index-controller/index)
  (GET "/account/signin" [request] account-contorller/signin-page)
  (POST "/account/signin" [request] account-contorller/signin)
  (route/not-found (page-not-found)))
