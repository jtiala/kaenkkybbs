(ns forum.services.users
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure :refer [GET]]
            [jeesql.core :refer [defqueries]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/users.sql")

(defn get-user
  "Get a user from the database."
  [db id]
  (let [result (get-user-query db {:id id})]
    {:result result}))

(defn get-users
  "Get a list of users from the database."
  [db]
  (let [result (get-users-query db)]
    {:result result}))

(defrecord Users []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/users/:id" [id]
                              (get-user db id)))
    (publish-service server (GET "/api/users" []
                              (get-users db)))
    this)
  (stop [this]
    this))
