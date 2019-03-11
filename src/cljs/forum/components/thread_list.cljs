(ns forum.components.thread-list
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]))

(defn component-did-mount []
  (actions/load-threads))

(defn render [{:keys [threads]}]
  (if threads
    [:section {:class "thread-list"}
     [:h2 (str "Threads (" (count threads) ")")]
     [:ul
      (for [thread threads]
        ^{:key (str "thread-" (:id thread))}
        [:li
         [:a {:href (str "#/threads/" (:id thread))} (str (:id thread) ": " (:title thread))]])]]
    [:span "loading threads..."]))

(defn component [state]
  (println "THREAD LIST: " state)
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

