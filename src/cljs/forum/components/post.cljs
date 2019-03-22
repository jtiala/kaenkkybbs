(ns forum.components.post
  (:require [forum.components.badge :as badge]
            [forum.actions :as actions]
            [forum.selectors :as selectors]))

(defn component [post user]
  (let [{:keys [user_id user_username user_role updated_at message]} post]
    [:li {:class "post-item"}
     [:span {:class "meta"}
      [:span {:class "posted_by"} (if user_username user_username "<Anonymous>") [badge/component user_role]]
      [:span {:class "updated_at"} (.toUTCString updated_at)]]
     [:p {:class "message"} message]
     (if
       (and
         (selectors/logged-in? user)
         (or
           (= (selectors/get-user-role user) "admin")
           (= (selectors/get-user-role user) "moderator")
           (= (selectors/get-user-id user) user_id)))
       [:span {:class "actions"}
        [:button {:on-click (fn [_] (actions/delete-post (:id post)))}
         "Delete message"]])]))

