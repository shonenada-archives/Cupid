(ns cupid.utils.cookies
  (:require [cupid.config :as config]
            [cupid.utils.cipher :as cipher :only [aes-encrypt aes-decrypt]]))

(defn ->cookie [value]
  {:value value
   :path "/"
   :max-age config/cookie-age})

(defn expired-cookie []
  {:value (cipher/aes-encrypt "expired" config/cookie-key)
   :path "/"
   :max-age 0
   :expires "Thu, 01 Jan 1970 00:00:00 GMT"})

(defn gen-cookie [input]
  (-> input
      str
      (cipher/aes-encrypt config/cookie-key)
      ->cookie))

(defn ->cookie-name [key-name]
  (str config/cookie-prefix (name key-name)))

(defn get-cookie [req key-name]
  (let [cookie-name (->cookie-name key-name)
        cookies (:cookies req)]
    (if-let [value (-> cookies (get cookie-name) :value)]
      (str (cipher/aes-decrypt value config/cookie-key)))))

(defn **add-cookie [resp req key-name cookie-entry]
  (let [cookie-name (->cookie-name key-name)
        cookies (conj (:cookies req) (resp :cookies))
        new-cookies (conj cookies {cookie-name cookie-entry})]
    (assoc resp :cookies new-cookies)))

(defn add-cookie [resp req key-name value]
  (let [cookie-entry (gen-cookie value)]
    (**add-cookie resp req key-name cookie-entry)))

(defn delete-cookie [resp req key-name]
  (**add-cookie resp req key-name (expired-cookie)))
