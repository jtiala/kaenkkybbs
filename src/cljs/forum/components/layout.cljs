(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.routing :as routing]))

(defn component []
  [:section {:id "layout"}
   [header/component]
   [:section {:id "main"}
    [routing/current-route]]])
