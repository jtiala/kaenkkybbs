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
      (println "OLD STATE: " old-state)
      (assoc old-state :threads threads))))

(defn load-thread [id]
  (println (str "Loading thread: " id))
  (api/api-get
    (str "/threads/" id) {} state/state
    (fn [previous-state result]
      (assoc previous-state :thread result))))

