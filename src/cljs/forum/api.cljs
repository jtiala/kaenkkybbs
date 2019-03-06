(ns forum.api
  (:require [forum.state :as state]
            [ajax.core :refer [GET POST json-response-format]]))

(defn report-ajax-error [error]
  (js/console.error error))

(defn- create-params [params state append?]
  {:params  params
   :handler (fn [response]
              (if append?
                (swap! state #(concat % (:result response)))
                (swap! state #(:result response))))
   :keyword? true
   :error-handler report-ajax-error})

(defn api-post [path params state append?]
  (POST (str "/api" path) (create-params params state append?)))

(defn api-get [path params state append?]
  (GET (str "/api" path) (create-params params state append?)))
