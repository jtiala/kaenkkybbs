(ns forum.components.thread
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.utils :as utils]
            [forum.selectors :as selectors]
            [forum.components.post :as post]
            [forum.components.new-post :as new-post]
            [forum.components.login :as login]
            [forum.components.badge :as badge]))

(defn component-did-mount [this]
  (actions/load-thread (-> this reagent/argv second deref (get-in [:thread :id]))))

(defn render [state]
  (let [{:keys [title user-username user-role updated-at]} (:thread @state)
        posts (sort-by :id (:posts (:thread @state)))
        user (:user @state)]
    [:section.thread
     [:div.thread-contents
      [:div.post-item.first
       [:span.meta
        [:span.started-by (if user-username user-username "<Anonymous>") [badge/component user-role]]
        [:span.updated-at (if updated-at (utils/format-timestamp updated-at))]]
       [:h2.title title]
       [:p.message (:message (first posts))]]

      (if (< (count posts) 2)
        [:p.no-responses "No responses yet."]
        [:ul
         (for [post (drop 1 posts)]
           ^{:key (str "post-" (:id post))}
           [post/component post user])])]

     (if (selectors/logged-in? user)
       [new-post/component state]
       [login/component state])]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

