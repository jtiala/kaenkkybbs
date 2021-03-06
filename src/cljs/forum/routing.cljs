(ns forum.routing
  (:require [forum.state :as state]
            [forum.actions :as actions]
            [forum.components.home :as home]
            [forum.components.thread :as thread]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType])
  (:import goog.history.Html5History)
  (:require-macros [secretary.core :refer [defroute]]))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn change-route [route]
  (println (str "Navigating to " route))
  (swap! state/state assoc :route route))

(defn router []
  (secretary/set-config! :prefix "#")

  (defroute home-path "/" []
    (change-route :home))

  (defroute thread-path "/threads/:id" [id]
    (actions/set-current-thread id)
    (change-route :thread))

  (hook-browser-navigation!))

(defmulti current-route
          (fn [state]
            (:route @state)))
(defmethod current-route :home [state]
  [home/component state])
(defmethod current-route :thread [state]
  [thread/component state])
