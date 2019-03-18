(ns forum.components.thread
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.components.post :as post]
            [forum.components.new-post :as new-post]))

(defn component-did-mount [this]
  (actions/load-thread (-> this reagent/argv second deref (get-in [:thread :id]))))

(defn render [state]
  (let [{:keys [title posts]} (:thread @state)]
    [:section {:class "thread"}
     [:h2 title]
     (for [p posts]
       ^{:key (str "post-" (:id p))}
       [post/component p])
     [new-post/component state]]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

