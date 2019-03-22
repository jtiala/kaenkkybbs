(ns forum.components.new-post
  (:require [forum.actions :as actions]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

(defn message-textarea [component-state]
  [:textarea {:id "message"
              :name "message"
              :on-change #(swap! component-state assoc :message (-> % .-target .-value))
              :value (:message @component-state)}])

(defn submit-button [component-state]
  [:button {:type "submit"
            :disabled (empty? (trim (:message @component-state)))
            :on-click (fn [event]
                        (.preventDefault event)
                        (actions/create-post (trim (:message @component-state)) (:thread @component-state) (:posted-by @component-state))
                        (swap! component-state assoc :message ""))}
   "Send"])

(defn component [state]
  (let [component-state (atom {:message ""
                               :thread (get-in @state [:thread :id])
                               :posted-by (get-in @state [:user :id] 0)})]
    (fn []
      [:div {:class "new-post"}
       [:h3 "Write a response"]
       [:form
        [:label {:for "message"} "Your message"]
        [message-textarea component-state]
        [submit-button component-state]]])))
