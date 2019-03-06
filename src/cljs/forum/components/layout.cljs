(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.components.thread-list :as thread-list]))

(defn component []
  [:div {:id "layout"}
   [header/component]
   [thread-list/component]])
