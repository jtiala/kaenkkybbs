(ns forum.actions
  (:require [forum.state :as state]
            [forum.api :as api]
            [forum.util :as util])
  (:require-macros [reagent.ratom :refer [reaction]]))

(def get-thread-count
  (reaction (count @state/threads)))

(defn set-current-thread [id]
  (println (str "Setting current thread: " id))
  (reset! state/current-thread (util/parse-int id)))

(def get-current-thread
  (reaction
    (first
      (filter (fn [thread]
                (= (:id thread) @state/current-thread))
              @state/threads))))

(defn load-threads []
  (println "Loading all threads")
  (api/api-get
    "/threads" {} state/threads
    (fn [_ result]
      result)))

(defn load-thread [id]
  (println (str "Loading thread: " id))
  (api/api-get
    (str "/threads/" id) {} state/threads
    (fn [previous-state result]
      (map
        #(if (= (:id %) (:id result)) result %)
        previous-state))))

