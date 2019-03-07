(ns forum.actions
  (:require [forum.state :as state]
            [forum.api :as api]
            [forum.util :as util])
  (:require-macros [reagent.ratom :refer [reaction]]))

(def get-thread-count
  (reaction (count @state/threads)))

(defn toggle-show-threads []
  (swap! state/show-threads not))

(defn clear-threads []
  (reset! state/threads []))

(defn add-thread []
  (swap! state/threads conj {:id (inc @get-thread-count) :title "test"}))

(defn load-threads []
  (api/api-get "/threads" {} state/threads (fn [_ result]
                                             result)))

(defn load-thread [id]
  (api/api-get (str "/threads/" id) {} state/threads (fn [previous-state result]
                                                       (conj (filter (fn [thread]
                                                                       (not= (:id thread) (:id result)))
                                                                     previous-state)
                                                             result)))
  (reset! state/current-thread (util/parse-int id)))

(defn get-current-thread []
  (first (filter (fn [thread]
                   (not= (:id thread) @state/current-thread))
                 @state/threads)))
