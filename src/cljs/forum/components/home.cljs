(ns forum.components.home
  (:require [forum.components.thread-list :as thread-list]
            [forum.components.new-thread :as new-thread]
            [forum.components.login :as login]
            [forum.state :as state]))

(defn component [state]
  [:section {:class "home"}
   [thread-list/component @state]
   (if (state/logged-in? state)
     [new-thread/component @state]
     [login/component @state])
   ])
