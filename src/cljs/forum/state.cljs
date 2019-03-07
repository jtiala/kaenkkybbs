(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce route (atom :home))

(defonce threads (atom []))

(defonce current-thread (atom nil))

(defonce show-threads (atom true))
