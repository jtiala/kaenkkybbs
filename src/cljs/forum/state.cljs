(ns forum.state
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce state (atom {:route :home}))
