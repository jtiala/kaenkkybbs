(ns forum.services.users
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/users.sql")

(defn get-users
  "Get a list of users from the database."
  [db]
  (let [result (get-users-query db)]
    {:result result}))

(defn get-user
  "Get a user from the database."
  [db id]
  (let [result (get-user-query db {:id id})]
    {:result (first result)}))

(defrecord Users []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/users" []
                              (get-users db)))
    (publish-service server (GET "/api/users/:id" [id :<< as-int]
                              (get-user db id)))
    this)
  (stop [this]
    this))
