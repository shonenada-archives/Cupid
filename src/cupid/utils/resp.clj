(ns cupid.utils.resp)

(defn resp-api [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json;charset=utf-8"}
   :body data})
