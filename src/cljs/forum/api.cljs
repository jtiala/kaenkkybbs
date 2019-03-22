(ns forum.api
  (:require [forum.state :as state]
            [ajax.core :refer [GET POST PUT DELETE json-response-format]]))

(defn report-ajax-error [error]
  (js/console.error error))

(defn- create-params [params state handler]
  {:params  params
   :handler (fn [response]
              (swap! state #(handler % (:result response))))
   :keyword? true
   :error-handler report-ajax-error})

(defn api-get [path params state handler]
  (GET (str "/api" path) (create-params params state handler)))

(defn api-post [path params state handler]
  (POST (str "/api" path) (create-params params state handler)))

(defn api-put [path params state handler]
  (PUT (str "/api" path) (create-params params state handler)))

(defn api-delete [path params state handler]
  (DELETE (str "/api" path) (create-params params state handler)))
