(ns cupid.controllers.account
  (:use [cupid.utils.resp])
  (:require [clojure.data.json :as json]
            [selmer.parser :refer [render-file]]
            [cupid.utils.cipher :as cipher]
            [cupid.utils.cookies :as cookies]
            [cupid.models.account :as account-model]
            [cupid.messages :as messages]))

(defn signup-page [request]
  (response (render-file "templates/account/signup.html" {})))

(defn signup [request]
  (let [params (:params request)
        username (:username params)
        password (:password params)
        fullname (:fullname params)
        email (:email params)
        mobile (:mobile params)]
    (if (or (empty? username)
            (empty? password)
            (empty? fullname)
            (empty? email)
            (empty? mobile))
      (response {:success false
                 :message messages/empty-fields})
      (let [account-by-username (account-model/get-by-username username)
            account-by-mobile (account-model/get-by-mobile mobile)
            account-by-email (account-model/get-by-email email)]
        (if (not (nil? account-by-username))
          (response {:success false
                     :message messages/username-exist})
          (if (not (nil? account-by-mobile))
            (response {:success false
                       :message messages/mobile-exist})
            (if (not (nil? account-by-email))
              (response {:success false
                         :message messages/email-exist})
              (let [account {:username username
                             :password (cipher/hash-str password)
                             :fullname fullname
                             :email email
                             :mobile mobile}
                    created-account (account-model/create account)]
                (response {:success true})))))
        ))))

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
