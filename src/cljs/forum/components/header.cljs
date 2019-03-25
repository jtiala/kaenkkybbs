(ns forum.components.header
  (:require [forum.selectors :as selectors]
            [forum.actions :as actions]))

(defn component [user]
  [:header#header
   [:h1
    [:a {:href "#"} "🍕 KaenkkyBBS"]]
   (if (selectors/logged-in? user)
     [:div.user
      [:span "Logged in as " (selectors/get-user-username user)]
      [:button {:on-click (fn [_] (actions/logout))}
       "Logout"]])])

