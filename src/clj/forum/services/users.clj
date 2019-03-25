(ns forum.services.users
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET POST]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.transit-util :refer [transit->clj]]
            [forum.utils :as utils]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/users.sql")

(defn get-users
  "Get a list of users from the database."
  [db]
  (let [result (get-users-query db)]
    (utils/format-response result)))

(defn get-user
  "Get a user from the database."
  [db id]
  (let [result (get-user-query db {:id id})]
    (utils/format-response (first result))))

(defn login
  "Get a user from the database based on email+password combination."
  [db email password]
  (let [result (login-query db {:email email :password password})]
    (utils/format-response (first result))))

(defrecord Users []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/users" []
                              (get-users db)))
    (publish-service server (GET "/api/users/:id" [id :<< as-int]
                              (get-user db id)))
    (publish-service server (POST "/api/users/login" {body :body}
                              (let [{email :email
                                     password :password} (transit->clj body)]
                                (login db email password))))
    this)
  (stop [this]
    this))
