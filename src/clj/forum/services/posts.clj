(ns forum.services.posts
  (:require [com.stuartsierra.component :as component]
            [compojure
             [core :as compojure :refer [GET]]
             [coercions :refer [as-int]]]
            [jeesql.core :refer [defqueries]]
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
    {:result result}))

(defrecord Posts []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/posts" []
                              (get-posts db)))
    (publish-service server (GET "/api/posts/:id" [id :<< as-int]
                              (get-post db id)))
    this)
  (stop [this]
    this))
