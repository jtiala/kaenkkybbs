(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce route (atom :home))

(defonce threads (atom []))

(defonce show-threads (atom true))
