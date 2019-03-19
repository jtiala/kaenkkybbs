(ns forum.actions
  (:require [forum.state :as state]
            [forum.api :as api]
            [forum.util :as util])
  (:require-macros [reagent.ratom :refer [reaction]]))

(defn set-current-thread [id]
  (println (str "Setting current thread: " id))
  (swap! state/state assoc-in [:thread :id] (util/parse-int id)))

(defn load-threads []
  (println "Loading all threads")
  (api/api-get
    "/threads" {} state/state
    (fn [old-state threads]
      (assoc old-state :threads threads))))

(defn load-thread [id]
  (println (str "Loading thread: " id))
  (api/api-get
    (str "/threads/" id) {} state/state
    (fn [previous-state result]
      (assoc previous-state :thread result))))

(defn create-thread [title started_by message]
  (api/api-post
    "/threads" {:title title :started_by started_by :message message} state/state
    (fn [previous-state result]
      (update-in previous-state [:threads] conj result))))

(defn create-post [message thread posted_by]
  (api/api-post
    "/posts" {:message message :thread thread :posted_by posted_by} state/state
    (fn [previous-state result]
      (update-in previous-state [:thread :posts] conj result))))
