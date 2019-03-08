(ns forum.components.thread-list
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]))

(defn component-did-mount []
  (actions/load-threads))

(defn render []
  [:section {:class "thread-list"}
   [:h2 (str "Threads (" @actions/get-thread-count ")")]
   [:ul
    (for [thread @state/threads]
      ^{:key (str "thread-" (:id thread))}
      [:li
       [:a {:href (str "#/threads/" (:id thread))} (str (:id thread) ": " (:title thread))]])]])

(defn component []
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

