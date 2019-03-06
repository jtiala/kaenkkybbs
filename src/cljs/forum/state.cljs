(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce threads (atom []))

(defonce show-threads (atom true))
