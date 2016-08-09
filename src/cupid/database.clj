(ns cupid.database
  (:use [korma.core]
        [korma.db :only [defdb mysql]])
  (:require [cupid.config :as config]))

(defdb cupid-db (mysql config/db-params))

(declare accounts staffs roles interviews)

(defentity accounts
  (database cupid-db)
  (table :account)
  (pk :id)
  (entity-fields :username
                 :password
                 :fullname
                 :email
                 :mobile
                 :token))

(defentity staffs
  (database cupid-db)
  (table :staff)
  (pk :id)
  (entity-fields :id
                 :staff_no
                 :fullname
                 :gender
                 :email
                 :mobile
                 :id_card
                 :employed_time
                 :job_name
                 :resume_url
                 :status))

(defentity roles
  (database cupid-db)
  (table :role)
  (pk :id)
  (entity-fields :name :remark))

(defentity interviews
  (database cupid-db)
  (table :interview)
  (pk :id)
  (entity-fields :staff_id
                 :interviewer_id
                 :title
                 :remark
                 :result
                 :interview_time))
