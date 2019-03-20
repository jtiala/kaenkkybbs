(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce state (atom {:route :home}))

(defn logged-in? [state]
  (> (get-in @state [:user :id]) 0))

(defn get-username [state]
  (get-in @state [:user :username]))
