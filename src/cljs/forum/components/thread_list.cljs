(ns forum.components.thread-list
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.actions :as actions]
            [forum.components.badge :as badge]))

(defn component-did-mount []
  (actions/load-threads))

(defn thread-item [thread]
  (let [{:keys [id user_username user_role updated_at title]} thread]
  [:li
   [:a {:class "thread-item" :href (str "#/threads/" id)}
    [:span {:class "meta"}
     [:span {:class "started_by"} (if user_username user_username "<Anonymous>") [badge/component user_role]]
     [:span {:class "updated_at"} (if updated_at (.toUTCString updated_at))]]
    [:h3 {:class "title"} title]]]))

(defn render [{:keys [threads]}]
  (if threads
    [:section {:class "thread-list"}
     [:ul
      (for [thread (reverse (sort-by :latest_post threads))]
        ^{:key (str "thread-" (:id thread))}
        [thread-item thread])]]
    [:span "Loading threads..."]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

