(ns cupid.controllers.account
  (:use [cupid.utils.resp])
  (:require [clojure.data.json :as json]
            [selmer.parser :refer [render-file]]
            [cupid.utils.cipher :as cipher]
            [cupid.utils.cookies :as cookies]
            [cupid.models.account :as account-model]
            [cupid.messages :as messages]))

(defn signin-page [request]
  (response (render-file "templates/account/signin.html" {})))

(defn signin [request]
  (let [params (:params request)
        username (:username params)
        password (:password params)]
    (if (or (empty? username) (empty? password))
      (response {:success false
                 :message messages/empty-fields})
      (if-let [account (account-model/get-by-username username)]
        (if (account-model/check-password account password)
          (let [token (cipher/gen-token request)
                resp (response {:success true})]
            (account-model/set-token account token)
            (-> resp
                (cookies/add-cookie request "token" token)
                (cookies/add-cookie request "username" username)))
          (response {:success false
                     :message messages/signin-fail}))
        (response {:success false
                   :message messages/signin-fail})))))
