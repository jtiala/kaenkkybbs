(ns ^:figwheel-hooks forum.core
  (:require [forum.components.layout :as layout]
            [forum.actions :as actions]
            [goog.dom :as gdom]
            [reagent.core :as reagent :refer [atom]]))

(defn initial-populate-state
  []
  (actions/load-threads))

(defn mount [el]
  (reagent/render-component [layout/component] el)
  (initial-populate-state))

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
