(ns forum.services.threads
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET POST DELETE]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.transit-util :refer [transit->clj]]
            [forum.utils :as utils]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/threads.sql")
(defqueries "queries/posts.sql")

(defn get-threads
  "Get a list of threads from the database."
  [db]
  (let [result (get-threads-query db)]
    (utils/format-response result)))

(defn get-thread
  "Get a thread and its posts from the database."
  [db id]
  (let [thread (get-thread-query db {:id id})
        posts (get-posts-by-thread-query db {:thread id})]
    (utils/format-response (when (first thread) (assoc (first thread) :posts posts)))))

(defn create-thread
  "Save a new thread and a starting post to the database."
  [db title started_by message]
  (let [user-id (if (= started_by 0) nil started_by)
        created-thread (create-thread-query<! db {:title title :started_by user-id})]
    (create-post-query<! db {:message message :thread (:id created-thread) :posted_by user-id})
    (get-thread db (:id created-thread))))

(defn delete-thread
  "Delete a thread from the database."
  [db id]
  (let [result (delete-thread-query! db {:id id})]
    (utils/format-response (= result 1))))

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
    (publish-service server (DELETE "/api/threads/:id" [id :<< as-int]
                              (delete-thread db id)))
    this)
  (stop [this]
    this))
