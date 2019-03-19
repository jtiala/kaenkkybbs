(ns forum.components.post
  (:require [forum.components.badge :as badge]))

(defn component [post]
  (let [{:keys [user_username user_role updated_at message]} post]
  [:li {:class "post-item"}
   [:span {:class "meta"}
    [:span {:class "posted_by"} (if user_username user_username "<Anonymous>") [badge/component user_role]]
    [:span {:class "updated_at"} (if updated_at (.toUTCString updated_at))]]
   [:p {:class "message"} message]]))

