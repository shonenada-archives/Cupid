(ns cupid.config
  (:use [environ.core :refer [env]]
        [cupid.utils.common :refer [->int]]))

(defonce web-host (env :cupid-web-host "0.0.0.0"))
(defonce web-port (->int (env :cupid-web-port "12500")))
