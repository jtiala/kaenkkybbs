(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.routing :as routing]))

(defn component []
  [:div {:id "layout"}
   [header/component]
   [routing/current-route]])
