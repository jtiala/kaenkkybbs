(ns ^:figwheel-hooks forum.core
  (:require
  ;  [forum.api :as api]
  ;  [forum.state :as state]
  ;  [forum.components.layout :as layout]
   [goog.dom :as gdom]
   [reagent.core :as reagent]))

; (defn initial-populate-state []
  ; (api/api-get "/posts" {} (fn [state result]
  ;                            (assoc state :posts result)))
  ; )

(defn get-app-element []
  (gdom/getElement "app"))

(defn asd []
  [:div {:id "layout"}
  ;  [header/component]
  ;  [posts/component]]
   [:h1 "ffff"]])


(defn mount [el]
  (reagent/component-component [asd] el)
  ; (initial-populate-state))
  )

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
