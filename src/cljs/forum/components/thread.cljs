(ns forum.components.thread
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.components.post :as post]
            [forum.components.new-post :as new-post]))

(defn component-did-mount [this]
  (actions/load-thread (-> this reagent/argv second deref (get-in [:thread :id]))))

(defn render [state]
  (let [{:keys [title user_username updated_at]} (:thread @state)
        posts (sort-by :id (:posts (:thread @state)))]
    [:section {:class "thread"}
     [:div {:class "thread-item"}
      [:span {:class "meta"}
       [:span {:class "started_by"} (if user_username user_username "<Anonymous>")]
       [:span {:class "updated_at"} (if updated_at (.toUTCString updated_at))]]
      [:h2 {:class "title"} title]
      [:p {:class "message"} (:message (first posts))]]

     (println posts)
     (if (< (count posts) 2)
       [:p "No responses yet."]
       [:ul
        (for [post (drop 1 posts)]
          ^{:key (str "post-" (:id post))}
          [post/component post]) ])
     [new-post/component state]]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

