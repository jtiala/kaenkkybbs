(ns forum.components.home
  (:require [forum.components.thread-list :as thread-list]))

(defn component []
  [:section {:class "home"}
   [thread-list/component]])
