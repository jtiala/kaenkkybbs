(ns forum.components.header
  (:require [forum.state :as state]
            [forum.actions :as actions]))

(defn component [state]
  [:header {:id "header"}
   [:h1
    [:a {:href "#"} "🍕 KaenkkyBBS"]]
   (if (state/logged-in? state)
     [:div {:class "user"}
      [:span "Logged in as " (state/get-username state)]
      [:button {:on-click (fn [_] (actions/logout))}
       "Logout"]])])

