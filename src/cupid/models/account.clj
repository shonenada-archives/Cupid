(ns cupid.models.account
  (:use [korma.core]
        [cupid.database])
  (:require [cupid.utils.common :as utils]
            [cupid.utils.cipher :as cipher]))

(defn get-by-id [id]
  (first (select accounts
                 (where {:id id}))))

(defn get-by-username [username]
  (first (select accounts
                 (where {:username username}))))

(defn get-by-email [email]
  (first (select accounts
                 (where {:email email}))))

(defn get-by-mobile [mobile]
  (first (select accounts
                 (where {:mobile mobile}))))

(defn username-exist? [username]
  (if-let [accounts (get-by-username username)]
    (true)
    (false)))

(defn email-exist? [email]
  (if-let [accounts (get-by-email email)]
    (true)
    (false)))

(defn mobile-exist? [mobile]
  (if-let [accounts (get-by-mobile mobile)]
    (true)
    (false)))

(defn create [account]
  (let [{id :generated_key} (insert accounts (values account))]
    (get-by-id id)))

(defn check-password [account raw-password]
  (cipher/check-hash raw-password (:password account)))

(defn set-token [account token]
  (update accounts
          (set-fields {:token token})
          (where {:id (:id account)})))

(defn check-token [account token]
  (let [account-token {:token account}]
    (= account-token token)))
