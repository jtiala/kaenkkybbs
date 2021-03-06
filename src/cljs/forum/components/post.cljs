(ns forum.components.post
  (:require [forum.components.badge :as badge]
            [forum.actions :as actions]
            [forum.selectors :as selectors]))

(defn component [post user]
  (let [{:keys [user-id user-username user-role updated-at message]} post]
    [:li.post-item
     [:span.meta
      [:span.posted-by (if user-username user-username "<Anonymous>") [badge/component user-role]]
      [:span.updated-at (.toUTCString updated-at)]]
     [:p.message  message]
     (if
       (and
         (selectors/logged-in? user)
         (or
           (= (selectors/get-user-role user) "admin")
           (= (selectors/get-user-role user) "moderator")
           (= (selectors/get-user-id user) user-id)))
       [:span.actions
        [:button {:on-click (fn [_] (actions/delete-post (:id post)))}
         "Delete message"]])]))

