(ns forum.components.home
  (:require [forum.components.thread-list :as thread-list]))

(defn component [state]
  [:section {:class "home"}
   [thread-list/component @state]])
