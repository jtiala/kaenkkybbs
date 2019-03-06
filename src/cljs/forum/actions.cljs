(ns forum.actions
  (:require [forum.state :as state]
            [forum.api :as api])
  (:require-macros [reagent.ratom :refer [reaction]]))

(def get-thread-count
  (reaction (count @state/threads)))

(defn toggle-show-threads []
  (swap! state/show-threads not))

(defn clear-threads []
  (swap! state/threads empty))

(defn add-thread []
  (swap! state/threads conj {:id (inc @get-thread-count) :title "test"}))

(defn load-threads []
  (api/api-get "/threads" {} state/threads true))
