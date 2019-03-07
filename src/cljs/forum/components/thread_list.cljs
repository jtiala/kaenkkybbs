(ns forum.components.thread-list
  (:require [forum.state :as state]
            [forum.actions :as actions]))

(defn toggle-button []
  [:button {:on-click (fn [_] (actions/toggle-show-threads))}
   (if @state/show-threads "Hide threads" "Show threads")])

(defn clear-button []
  [:button {:on-click (fn [_] (actions/clear-threads))}
   "Clear threads"])

(defn add-button []
  [:button {:on-click (fn [_] (actions/add-thread))}
   "Add a thread"])

(defn component []
  [:section {:class "thread-list"}
   [:h2 (str "Threads (" @actions/get-thread-count ")")]
   [toggle-button]
   [clear-button]
   [add-button]
   (if @state/show-threads
     [:ul
      (for [thread @state/threads]
        ^{:key (str "thread-" (:id thread))}
        [:li
         [:a {:href (str "#/threads/" (:id thread))} (str (:id thread) ": " (:title thread))]])])])
