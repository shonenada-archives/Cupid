(ns cupid.utils.common
  (:require [clojure.data.generators :as dg :only [uuid]]))

(defn ->int [s]
  (try
    (cond
      (string? s) (Integer/parseInt s)
      (instance? Integer s) (s)
      (instance? Long s) (.intValue ^Long s)
      :else nil)
    (catch Exception e
      nil)))

(defn uuid []
  (dg/uuid))
