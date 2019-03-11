(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.routing :as routing]
            [forum.state :as state]))

(defn component []
  [:section {:id "layout"}
   [header/component]
   [:section {:id "main"}
    [routing/current-route state/state]]])
