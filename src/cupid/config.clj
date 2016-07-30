(ns cupid.config
  (:use [environ.core :refer [env]]
        [cupid.utils.common :refer [->int]]))

(defonce cookie-age (* 24 60 60))
(defonce cookie-prefix "cupid_")
(defonce cookie-key (env :cupid-cookie-key "cookie-key")) 

(defonce secret-key (env :cupid-secret-key ""))

(defonce web-host (env :cupid-web-host "0.0.0.0"))
(defonce web-port (->int (env :cupid-web-port "12500")))

(defonce db-type (env :cupid-db-type "mysql"))
(defonce db-host (env :cupid-db-host "127.0.0.1"))
(defonce db-port (->int (env :cupid-db-port "3306")))
(defonce db-name (env :cupid-db-name "cupid"))
(defonce db-username (env :cupid-db-username ""))
(defonce db-password (env :cupid-db-password ""))

(cond
  (= db-type "mysql") (defonce db-subname (str "//" db-host ":" db-port "/" db-name))
  :else (db-name))

(defonce db-params
  {:subprotocol db-type
   :subname db-subname
   :db db-name
   :user db-username
   :password db-password})
