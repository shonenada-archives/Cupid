(ns cupid.controllers.staff
  (:use [ring.util.response])
  (:require [selmer.parser :refer [render-file]]
            [clojure.tools.logging :as logging]
            [cupid.models.staff :as staff-model]
            [cupid.messages :as messages]
            [cupid.utils.common :refer [->int]]))

(defn staffs [request]
  (let [params (:params request)
        start (:start params)
        size (:size params)
        staffs (staff-model/get-list start size)]
    (response (render-file "staffs/list.html" {:staffs staffs}))))

(defn create-staff [request]
  (let [params (:params request)
        staff-no (:staff_no params)
        fullname (:fullname params)
        gender (:gender params)
        email (:email params)
        mobile (:mobile params)
        id-card (:id_card params)
        employed-time (:employed_time params)
        job-name (:job_name params)
        resume-url (:resume params)
        status (:status params)]
    (if (or (empty? staff-no)
            (empty? fullname)
            (empty? gender)
            (empty? mobile))
      (response {:success false
                 :message messages/empty-fields})
      (let [staff-by-staff-no (staff-model/get-by-staff-no staff-no)
            staff-by-mobile (staff-model/get-by-mobile mobile)]
        (if (not (nil? staff-by-staff-no))
          (response {:success false
                     :message messages/staff-no-exists})
          (if (not (nil? staff-by-mobile))
            (response {:success false
                       :message messages/mobile-exist})
            (let [staff {:staff_no staff-no
                         :fullname fullname
                         :gender gender
                         :email email
                         :mobile mobile
                         :id_card id-card
                         :employed_time employed-time
                         :job_name job-name
                         :resume_url resume-url
                         :status status}
                  created-staff (staff-model/create-staff staff)]
              (response {:success true}))))))))
