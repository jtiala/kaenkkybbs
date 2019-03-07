(ns forum.services.threads
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/threads.sql")
(defqueries "queries/posts.sql")

(defn get-threads
  "Get a list of threads from the database."
  [db]
  (let [result (get-threads-query db)]
    {:result result}))

(defn get-thread
  "Get a thread and its posts from the database."
  [db id]
  (let [thread (get-thread-query db {:id id})
        posts (get-posts-by-thread-query db {:thread id})]
    {:result (assoc (first thread) :posts posts)}))

(defrecord Threads []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/threads" []
                              (get-threads db)))
    (publish-service server (GET "/api/threads/:id" [id :<< as-int]
                              (get-thread db id)))
    this)
  (stop [this]
    this))
