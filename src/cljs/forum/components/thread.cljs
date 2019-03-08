(ns forum.components.thread
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.components.post :as post]))

(defn component-did-mount []
  (actions/load-thread @state/current-thread))

(defn render []
  (let [thread @actions/get-current-thread]
    (println thread)
    [:section {:class "thread"}
     [:h2 (:title thread)]
     (for [p (:posts thread)]
       ^{:key (str "post-" (:id p))}
       [post/component p])]))

(defn component []
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

