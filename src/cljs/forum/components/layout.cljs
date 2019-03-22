(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.components.footer :as footer]
            [forum.routing :as routing]
            [forum.state :as state]))

(defn component []
  [:section {:id "layout"}
   [header/component (:user @state/state)]
   [:section {:id "main"}
    [routing/current-route state/state]]
   [footer/component]])
