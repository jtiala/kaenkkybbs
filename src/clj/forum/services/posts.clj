(ns forum.services.posts
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET POST DELETE]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
            [forum.transit-util :refer [transit->clj]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/posts.sql")

(defn get-posts
  "Get a list of posts from the database."
  [db]
  (let [result (get-posts-query db)]
    {:result result}))

(defn get-post
  "Get a post from the database."
  [db id]
  (let [result (get-post-query db {:id id})]
    {:result (first result)}))

(defn create-post
  "Save a new post to the database."
  [db message thread posted_by]
  (let [user-id (if (= posted_by 0) nil posted_by)
        created-post (create-post-query<! db {:message message :thread thread :posted_by user-id})]
    (get-post db (:id created-post))))

(defn delete-post
  "Delete a post from the database."
  [db id]
  (let [result (delete-post-query! db {:id id})]
    {:result (= result 1)}))

(defrecord Posts []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/posts" []
                              (get-posts db)))
    (publish-service server (GET "/api/posts/:id" [id :<< as-int]
                              (get-post db id)))
    (publish-service server (POST "/api/posts" {body :body}
                              (let [{message :message
                                     thread :thread
                                     posted_by :posted_by} (transit->clj body)]
                                (create-post db message thread posted_by))))
    (publish-service server (DELETE "/api/posts/:id" [id :<< as-int]
                              (delete-post db id)))
    this)
  (stop [this]
    this))
