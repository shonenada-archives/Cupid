(ns cupid.models.staff
  (:use [korma.core]
        [cupid.database]))

(defn get-list
  [start size]
  (select staffs
          (offset start)
          (limit size)))

(defn get-by-id [id]
  (first (select staffs 
                 (where {:id id}))))

(defn get-by-staff-no [staff-no]
  (first (select staffs
                 (where {:staff-no staff-no}))))

(defn get-by-email [email]
  (first (select staffs
                 (where {:email email}))))

(defn get-by-mobile [mobile]
  (first (select staffs
                 (where {:mobile mobile}))))
