(ns forum.components.posts
  (:require [forum.state :as state]
            [forum.actions :as actions]))

(defn toggle-posts-button []
  [:button {:on-click (fn [_] (actions/toggle-posts))}
   (if state/show-posts "Hide posts" "Show posts")])

(defn component []
  [:div {:class "posts"}
   [:h2 (str "Posts: " (actions/get-post-count))]
   [toggle-posts-button]
   (if state/show-posts
     [:ul
      (for [post state/posts]
        ^{:key (str "post-" (:id post))}
        [:li
         [:a {:href "/"} (str (:id post) ": " (:title post))]])])])
