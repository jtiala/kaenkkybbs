(ns forum.selectors)

(defn logged-in? [user]
  (> (:id user) 0))

(defn get-user-id [user]
  (:id user))

(defn get-user-username [user]
  (:username user))

(defn get-user-role [user]
  (:role user))

