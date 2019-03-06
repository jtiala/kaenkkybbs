(ns forum.components.layout
  (:require [forum.components.header :as header]
            [forum.components.thread-list :as thread-list]))

(defn render []
  [:div {:id "layout"}
   [header/render]
   [thread-list/render]])
