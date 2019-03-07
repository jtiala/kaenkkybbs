(ns forum.components.thread
  (:require [forum.actions :as actions]
            [forum.components.post :as post]))

(defn component []
  [:section {:class "thread"}
   (println (actions/get-current-thread))
   [:h2 (:title (actions/get-current-thread))]
   (for [p (:posts (actions/get-current-thread))]
     ^{:key (str "post-" (:id p))}
     [post/component p])])

