(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.components.footer :as footer]
            [forum.routing :as routing]
            [forum.state :as state]))

(defn component []
  [:section#layout
   [header/component (:user @state/state)]
   [:section#main
    [routing/current-route state/state]]
   [footer/component]])
