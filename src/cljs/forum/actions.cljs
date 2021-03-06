(ns forum.actions
  (:require [forum.state :as state]
            [forum.api :as api]
            [forum.utils :as utils])
  (:require-macros [reagent.ratom :refer [reaction]]))

(defn set-current-thread [id]
  (println (str "Setting current thread: " id))
  (swap! state/state assoc-in [:thread :id] (utils/parse-int id)))

(defn load-threads []
  (println "Loading all threads")
  (api/api-get
    "/threads" {} state/state
    (fn [old-state result]
      (assoc old-state :threads result))))

(defn load-thread [id]
  (println (str "Loading thread: " id))
  (api/api-get
    (str "/threads/" id) {} state/state
    (fn [previous-state result]
      (assoc previous-state :thread result))))

(defn create-thread [title started-by message]
  (api/api-post
    "/threads" {:title title :started-by started-by :message message} state/state
    (fn [previous-state result]
      (update-in previous-state [:threads] conj result))))

(defn delete-thread [id]
  (api/api-delete
    (str "/threads/" id) {} state/state
    (fn [previous-state result]
      (if (= result true)
        (update-in previous-state [:threads] (partial filter #(not= (:id %) id)))
        previous-state))))

(defn create-post [message thread posted-by]
  (api/api-post
    "/posts" {:message message :thread thread :posted-by posted-by} state/state
    (fn [previous-state result]
      (update-in previous-state [:thread :posts] conj result))))

(defn delete-post [id]
  (api/api-delete
    (str "/posts/" id) {} state/state
    (fn [previous-state result]
      (if (= result true)
        (update-in previous-state [:thread :posts] (partial filter #(not= (:id %) id)))
        previous-state))))

(defn login [email password]
  (api/api-post
    "/users/login" {:email email :password password} state/state
    (fn [previous-state result]
      (assoc previous-state :user result))))

(defn logout []
  (swap! state/state #(assoc % :user nil)))
