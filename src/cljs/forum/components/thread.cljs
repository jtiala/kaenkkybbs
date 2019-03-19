(ns forum.components.thread
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.utils :as utils]
            [forum.components.post :as post]
            [forum.components.new-post :as new-post]
            [forum.components.badge :as badge]))

(defn component-did-mount [this]
  (actions/load-thread (-> this reagent/argv second deref (get-in [:thread :id]))))

(defn render [state]
  (let [{:keys [title user_username user_role updated_at]} (:thread @state)
        posts (sort-by :id (:posts (:thread @state)))]
    [:section {:class "thread"}
     [:div {:class "thread-contents"}
      [:div {:class "post-item first"}
       [:span {:class "meta"}
        [:span {:class "started_by"} (if user_username user_username "<Anonymous>") [badge/component user_role]]
        [:span {:class "updated_at"} (if updated_at (utils/format-timestamp updated_at))]]
       [:h2 {:class "title"} title]
       [:p {:class "message"} (:message (first posts))]]

      (if (< (count posts) 2)
        [:p {:class "no-responses"} "No responses yet."]
        [:ul
         (for [post (drop 1 posts)]
           ^{:key (str "post-" (:id post))}
           [post/component post]) ])]
     [new-post/component state]]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

