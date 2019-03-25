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
  (let [{:keys [id user-id user-username user-role post-count latest-post title]} thread]
  [:li
   [:a.thread-item {:href (str "#/threads/" id)}
    [:span.meta
     [:span.started-by (if user-username user-username "<Anonymous>") [badge/component user-role]]
     [:span.latest-post post-count " messages, latest at " (utils/format-timestamp latest-post)]]
    [:h3.title title]
     (if
       (and
         (selectors/logged-in? user)
         (or
           (= (selectors/get-user-role user) "admin")
           (= (selectors/get-user-role user) "moderator")
           (= (selectors/get-user-id user) user-id)))
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
      (for [thread (reverse (sort-by :latest-post threads))]
        ^{:key (str "thread-" (:id thread))}
        [thread-item thread user])]]
    [:span "Loading threads..."]))

(defn component [state]
  (reagent/create-class {:reagent-render render
                         :component-did-mount component-did-mount}))

