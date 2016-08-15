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

  (GET "/account/signup" [] account-controller/signup-page)
  (POST "/account/signup" [] account-controller/signup)

  (GET "/account/signin" [] account-controller/signin-page)
  (POST "/account/signin" [] account-controller/signin)

  (GET "/account/signout" [] account-controller/signout)

  (GET "/staffs" [] staff-controller/staffs)
  (POST "/staffs" [] staff-controller/create-staff)
  (GET "/staffs/:id" [request id] (staff-controller/view-staff request id))

  (route/resources "/")
  (route/not-found (page-not-found)))
