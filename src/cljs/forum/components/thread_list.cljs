(ns forum.components.thread-list
  (:require [reagent.core :as reagent]
            [forum.state :as state]
            [forum.selectors :as selectors]
            [forum.actions :as actions]
            [forum.utils :as utils]
            [forum.components.badge :as badge]))

(defn component-did-mount []
  (actions/load-threads))

(defn thread-item [thread user]
  (let [{:keys [id user_id user_username user_role post_count latest_post title]} thread]
  [:li
   [:a.thread-item {:href (str "#/threads/" id)}
    [:span.meta
     [:span.started_by (if user_username user_username "<Anonymous>") [badge/component user_role]]
     [:span.latest_post post_count " messages, latest at " (utils/format-timestamp latest_post)]]
    [:h3.title title]
     (if
       (and
         (selectors/logged-in? user)
         (or
           (= (selectors/get-user-role user) "admin")
           (= (selectors/get-user-role user) "moderator")
           (= (selectors/get-user-id user) user_id)))
       [:span.actions
        [:button {:on-click (fn [event]
                              (.preventDefault event)
                              (actions/delete-thread (:id thread)))}
         "Delete thread"]])
    ]]))

(defn render [{:keys [threads user]}]
  (if threads
    [:section.thread-list
     [:ul
      (for [thread (reverse (sort-by :latest_post threads))]
        ^{:key (str "thread-" (:id thread))}
        [thread-item thread user])]]
    [:span "Loading threads..."]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

