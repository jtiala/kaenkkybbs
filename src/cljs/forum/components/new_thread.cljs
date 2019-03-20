(ns forum.components.new_thread
  (:require [forum.actions :as actions]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

(defn title-input [component-state]
  [:input {:id "title"
           :name "title"
           :on-change #(swap! component-state assoc :title (-> % .-target .-value))
           :value (:title @component-state)}])

(defn message-textarea [component-state]
  [:textarea {:id "message"
              :name "message"
              :on-change #(swap! component-state assoc :message (-> % .-target .-value))
              :value (:message @component-state)}])

(defn submit-button [component-state]
  [:button {:type "submit"
            :disabled (or
                        (empty? (trim (:title @component-state)))
                        (empty? (trim (:message @component-state))))
            :on-click (fn [event]
                        (.preventDefault event)
                        (actions/create-thread (trim (:title @component-state)) (:started-by @component-state) (trim (:message @component-state)))
                        (swap! component-state assoc :title "" :message ""))}
   "Send"])

(defn component [state]
  (let [component-state (atom {:title ""
                               :started-by (get-in state [:user :id] 0)
                               :message ""})]
    (fn []
      [:div {:class "new-thread"}
       [:h3 "Start a new thread"]
       [:form
        [:label {:for "title"} "Title"]
        [title-input component-state]
        [:label {:for "message"} "Your message"]
        [message-textarea component-state]
        [submit-button component-state]]])))
