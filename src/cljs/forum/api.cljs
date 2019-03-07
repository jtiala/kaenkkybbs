(ns forum.api
  (:require [forum.state :as state]
            [ajax.core :refer [GET POST json-response-format]]))

(defn report-ajax-error [error]
  (js/console.error error))

(defn- create-params [params state handler]
  {:params  params
   :handler (fn [response]
              (swap! state #(handler % (:result response))))
   :keyword? true
   :error-handler report-ajax-error})

(defn api-post [path params state handler]
  (POST (str "/api" path) (create-params params state handler)))

(defn api-get [path params state handler]
  (GET (str "/api" path) (create-params params state handler)))
