(ns cupid.routes
  (:use [compojure.core])
  (:require [compojure.route :as route]
            [cupid.controllers.index :as index-controller]
            [cupid.controllers.account :as account-controller]
            [cupid.controllers.staff :as staff-controller]))

(defn page-not-found []
  "Page not found.")

(defroutes app-routes
  (GET "/" [request] index-controller/index)

  (GET "/account/signup" [request] account-controller/signup-page)
  (POST "/account/signup" [request] account-controller/signup)

  (GET "/account/signin" [request] account-controller/signin-page)
  (POST "/account/signin" [request] account-controller/signin)

  (GET "/account/signout" [request] account-controller/signout)

  (GET "/staffs" [request] staff-controller/staffs)
  (POST "/staffs" [request] staff-controller/create-staff)

  (route/resources "/")
  (route/not-found (page-not-found)))
