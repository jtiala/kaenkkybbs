(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce title (atom "KaenkkyBBS"))

(defonce threads (atom [{:id 0 :title "test"}]))

(defonce show-threads (atom true))
