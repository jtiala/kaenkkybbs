(ns forum.components.login
  (:require [forum.actions :as actions]
            [forum.utils :as utils]
            [clojure.string :refer [trim]]
            [reagent.core :as reagent :refer [atom]]))

(defn email-input [component-state]
  [:input {:id "email"
           :name "email"
           :on-change #(swap! component-state assoc :email (-> % .-target .-value))
           :value (:email @component-state)}])

(defn password-input [component-state]
  [:input {:id "password"
           :name "password"
           :type "password"
           :on-change #(swap! component-state assoc :password (-> % .-target .-value))
           :value (:password @component-state)}])

(defn submit-button [component-state]
  [:button {:type "submit"
            :disabled (or
                        (empty? (trim (:email @component-state)))
                        (empty? (:password @component-state)))
            :on-click (fn [event]
                        (.preventDefault event)
                        (actions/login (trim (:email @component-state)) (:password @component-state))
                        (swap! component-state assoc :email "" :password ""))}
   "Login"])

(defn component [state]
  (let [component-state (atom {:email ""
                               :password ""})]
    (fn []
      [:div {:class "login"}
       [:h3 "Login"]
       [:form
        [:label {:for "email"} "Email"]
        [email-input component-state]
        [:label {:for "password"} "Password"]
        [password-input component-state]
        [submit-button component-state]]])))
