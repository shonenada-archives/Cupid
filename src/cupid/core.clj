(ns cupid.core
  (:use [ring.adapter.jetty]
        [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
        [ring.middleware.session :only [wrap-session]]
        [ring.middleware.session.cookie :only [cookie-store]]
        [cupid.routes])
  (:require [clojure.tools.logging :as log]
            [compojure.handler :as handler]
            [cupid.config :as config]))

(defn -main [& args]
  (log/info (str "Server is running at " config/web-host ":" config/web-port))
  (println "Hello, World!"))
