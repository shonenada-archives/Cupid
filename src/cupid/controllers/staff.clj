(ns cupid.controllers.staff
  (:use [ring.util.response])
  (:require [selmer.parser :refer [render-file]]
            [cupid.models.staff :as staff-model]
            [clojure.tools.logging :as logging]))

(defn staffs [request]
  (let [params (:params request)
        start (:start params)
        size (:size params)
        staffs (staff-model/get-list start size)
        _ (logging/debug staffs)]
    (response (render-file "templates/staffs/list.html" {:staffs staffs}))))
