(ns forum.services.threads
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure :refer [GET]]
            [jeesql.core :refer [defqueries]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/threads.sql")

(defn get-thread
  "Get a thread from the database."
  [db id]
  (let [result (get-thread-query db {:id id})]
    {:result result}))

(defn get-threads
  "Get a list of threads from the database."
  [db]
  (let [result (get-threads-query db)]
    {:result result}))

(defrecord Threads []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/threads/:id" [id]
                              (get-thread db id)))
    (publish-service server (GET "/api/threads" []
                              (get-threads db)))
    this)
  (stop [this]
    this))
