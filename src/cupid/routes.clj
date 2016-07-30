(ns cupid.routes
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [cupid.controllers.index :as index-controller]
            [cupid.controllers.account :as account-controller]))

(defn page-not-found []
  "Page not found.")

(defroutes app-routes
  (GET "/" [request] index-controller/index)

  (GET "/account/signup" [request] account-controller/signup-page)
  (POST "/account/signup" [request] account-controller/signup)

  (GET "/account/signin" [request] account-controller/signin-page)
  (POST "/account/signin" [request] account-controller/signin)

  (route/not-found (page-not-found)))
