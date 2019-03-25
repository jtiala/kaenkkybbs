(ns forum.components.home
  (:require [forum.components.thread-list :as thread-list]
            [forum.components.new-thread :as new-thread]
            [forum.components.login :as login]
            [forum.selectors :as selectors]))

(defn component [state]
  [:section.home
   [thread-list/component @state]
   (if (selectors/logged-in? (:user @state))
     [new-thread/component @state]
     [login/component @state])])

