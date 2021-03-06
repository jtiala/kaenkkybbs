(ns forum.http-server
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as http]
            [ring.util.response :as response]
            [compojure.core :as compojure :refer [GET POST]]
            [compojure.route :as route]
            [forum.transit-util :as transit]
            [environ.core :refer [env]]))

(def port (if (env :port) (Integer/parseInt (env :port)) 8080))

(defprotocol HttpServices
  (publish-service [this f]))

(defrecord Server [routes]
  component/Lifecycle
  (start [this]
    (let [stop-server (http/run-server
                        (fn [req]
                          (let [response (apply compojure/routing req @routes)]
                            (if (and (re-find #"^/api/" (:uri req))
                                     (= 200 (:status response)))
                              (cond-> response
                                (empty? (:headers response)) (assoc :headers {"Content-Type" "application/transit+json"})
                                (empty? (:body response)) (assoc :body (transit/clj->transit (dissoc response :status :headers :body)))
                                true (select-keys #{:status :headers :body}))
                              response)))
                        {:port port})]
      (assoc this :stop-server stop-server)))
  (stop [{:keys [stop-server] :as this}]
    (stop-server)
    this)
  HttpServices
  (publish-service [this f]
    (swap! routes (fn [old-routes]
                    (cons f old-routes)))))

(defn create-server []
  (->Server (atom [(GET "/" [] (response/redirect "/index.html"))
                   (route/resources "/")
                   (route/not-found "Page not found")])))
