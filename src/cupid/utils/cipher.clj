(ns cupid.utils.cipher
  (:use [cupid.config :as config]
        [cupid.utils.common :as utils]
        [cupid.utils.date :as date])
  (:import [org.apache.commons.codec.binary Base64]
           [java.security MessageDigest SecureRandom]
           [javax.crypto Cipher KeyGenerator SecretKey]
           [javax.crypto.spec SecretKeySpec]
           [org.mindrot.jbcrypt BCrypt]))

(defn gen-salt
  ([]
   (BCrypt/gensalt))
  ([size]
   (BCrypt/gensalt size)))

(defn hash-str [raw-str]
  (let [salt (gen-salt)]
    (BCrypt/hashpw raw-str salt)))

(defn check-hash [raw-str hash-str]
  (BCrypt/checkpw raw-str hash-str))

(defn b64-encode [^bytes b]
  {:tag String}
  (Base64/encodeBase64String b))

(defn b64-decode [^String s]
  {:tag bytes}
  (Base64/decodeBase64 s))

(defn get-raw-key [seed]
  (let [keygen (KeyGenerator/getInstance "AES")
        secret-random (SecureRandom/getInstance "SHA1PRNG")]
    (.setSeed secret-random (.getBytes seed "UTF-8"))
    (.init keygen 128 secret-random)
    (.. keygen generateKey getEncoded))) ;; (-> keygen/generateKey (.getEncoded))

(defn get-cipher [mode seed]
  (let [key-spec (SecretKeySpec. (get-raw-key seed) "AES")
        cipher (Cipher/getInstance "AES")]
    (.init cipher mode key-spec)
    cipher))

(defn aes-encrypt [plain-text encrypt-key]
  (let [text-bytes (.getBytes plain-text "UTF-8")
        cipher (get-cipher Cipher/ENCRYPT_MODE encrypt-key)]
    (b64-encode (.doFinal cipher text-bytes))))

(defn aes-decrypt [encrypted-text encrypt-key]
  (let [cipher (get-cipher Cipher/DECRYPT_MODE encrypt-key)]
    (String. (.doFinal cipher (b64-decode encrypted-text)))))

(defn gen-token [req]
  (let [remote-addr (:remote-addr req)
        raw-token (str remote-addr utils/uuid date/now)]
    (aes-encrypt raw-token config/secret-key)))
