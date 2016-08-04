(ns cupid.core
  (:use [ring.adapter.jetty]
        [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
        [ring.middleware.params :refer [wrap-params]]
        [ring.middleware.session :only [wrap-session]]
        [ring.middleware.session.cookie :only [cookie-store]]
        [cupid.routes])
  (:require [clojure.java.io :refer [resource]]
            [clojure.tools.logging :as log]
            [compojure.handler :as handler]
            [selmer.parser :refer [set-resource-path! cache-off!]]
            [cupid.config :as config]))

(selmer.parser/cache-off!)
(selmer.parser/set-resource-path! (resource "templates"))

(defn wrap-cookie
  [handler]
  (wrap-session handler {:cookie-attrs {:secure true}
                         :cookie-name "cupid-cookie"
                         :store (cookie-store)}))

(def app (handler/site (-> app-routes
                           wrap-cookie
                           wrap-params
                           wrap-json-response
                           wrap-json-params)))

(defn -main [& args]
  (log/info (str "Server is running at " config/web-host ":" config/web-port))
  (run-jetty app {:port config/web-port}))
