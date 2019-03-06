(ns forum.components.header
  (:require [forum.state :as state]))

(defn component []
  [:header
   [:h1 @state/title]])
