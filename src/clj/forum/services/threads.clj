(ns forum.services.threads
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET POST]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.transit-util :refer [transit->clj]]
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

(defn create-thread
  "Save a new thread to the database."
  [db title started_by message]
  (let [thread (create-thread-query<! db {:title title :started_by (if (= started_by 0) nil started_by)})
        post (create-post-query<! db {:message message :thread (:id thread) :posted_by (if (= started_by 0) nil started_by)})]
    {:result {:thread thread :post post}}))

(defrecord Threads []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/threads" []
                              (get-threads db)))
    (publish-service server (GET "/api/threads/:id" [id :<< as-int]
                              (get-thread db id)))
    (publish-service server (POST "/api/threads" {body :body}
                              (let [{title :title
                                     started_by :started_by
                                     message :message} (transit->clj body)]
                                (create-thread db title started_by message))))
    this)
  (stop [this]
    this))
