(ns forum.components.header
  (:require [forum.state :as state]))

(defn render []
  [:header
   [:h1 @state/title]])
