(ns forum.components.new_thread
  (:require [forum.actions :as actions]
            [forum.util :as util]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

(defn user-selected? [id component-state]
  (= id (:started-by @component-state)))

(defn create-user-option [[id name] component-state]
  [:option {:value id :selected (user-selected? id component-state)} name])

(defn started-by-select [component-state]
  [:select {:id "started_by"
            :name "started_by"
            :on-change #(swap! component-state assoc :started-by (-> % .-target .-value util/parse-int))}
   (create-user-option [0 "<Anonymous>"] component-state)
   (create-user-option [1 "Kaenkkykeisari"] component-state)
   (create-user-option [2 "ananas666"] component-state)
   (create-user-option [3 "PizzaKuski"] component-state)])

(defn title-input [component-state]
  [:input {:id "title"
           :name "title"
           :on-change #(swap! component-state assoc :title (-> % .-target .-value))
           }])

(defn message-textarea [component-state]
  [:textarea {:id "message"
              :name "message"
              :on-change #(swap! component-state assoc :message (-> % .-target .-value))
              :value (:message @component-state)}])

(defn submit-button [component-state]
  [:button {:type "submit"
            :on-click (fn [event]
                        (.preventDefault event)
                        (actions/create-thread (trim (:title @component-state)) (:started-by @component-state) (trim (:message @component-state)))
                        (swap! component-state assoc :title "" :message "" :started-by 0))}
   "Send"])

(defn component [state]
  (let [component-state (atom {:title ""
                               :started-by 0
                               :message ""})]
    (fn []
      [:div {:class "new-thread"}
       [:h3 "Start a new thread"]
       [:form
        [:label {:for "started_by"} "Send as (tmp)"]
        [started-by-select component-state]
        [:label {:for "title"} "Title"]
        [title-input component-state]
        [:label {:for "message"} "Your message"]
        [message-textarea component-state]
        [submit-button component-state]]])))