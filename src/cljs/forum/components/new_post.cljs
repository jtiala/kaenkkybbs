(ns forum.components.new-post
  (:require [forum.actions :as actions]
            [forum.utils :as utils]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

(defn create-user-option [[id name] component-state]
  [:option {:value id} name])

(defn posted-by-select [component-state]
  [:select {:id "posted_by"
            :name "posted_by"
            :value (:posted-by @component-state)
            :on-change #(swap! component-state assoc :posted-by (-> % .-target .-value utils/parse-int))}
   (create-user-option [0 "<Anonymous>"] component-state)
   (create-user-option [1 "Kaenkkykeisari"] component-state)
   (create-user-option [2 "ananas666"] component-state)
   (create-user-option [3 "PizzaKuski"] component-state)])

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
                        (swap! component-state assoc :message "" :posted-by 0))}
   "Send"])

(defn component [state]
  (let [component-state (atom {:message ""
                               :thread (get-in @state [:thread :id])
                               :posted-by 0})]
    (fn []
      [:div {:class "new-post"}
       [:h3 "Write a response"]
       [:form
        [:label {:for "posted_by"} "Send as (tmp)"]
        [posted-by-select component-state]
        [:label {:for "message"} "Your message"]
        [message-textarea component-state]
        [submit-button component-state]]])))
