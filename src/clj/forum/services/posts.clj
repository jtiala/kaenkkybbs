(ns forum.services.posts
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure :refer [GET]]
            [jeesql.core :refer [defqueries]]
            [forum.http-server :refer [publish-service]]))

(defqueries "queries/posts.sql")

(defn get-post
  ([db] (get-post db :all))
  ([db id]
   (let [result (if (= :all id)
                  (all-posts db)
                  (post-by-id db {:id id}))]
     {:result result})))

(defrecord Post []
  component/Lifecycle
  (start [{server :http-server
           db :db
           :as this}]
    (publish-service server (GET "/api/posts" []
                              (get-post db)))
    (publish-service server (GET "/api/posts/:id" [id]
                              (get-post db id)))
    this)
  (stop [this]
    this))
