(ns forum.components.home
  (:require [forum.components.thread-list :as thread-list]
            [forum.components.new-thread :as new-thread]))

(defn component [state]
  [:section {:class "home"}
   [thread-list/component @state]
   [new-thread/component @state]])
