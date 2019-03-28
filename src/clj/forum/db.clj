(ns forum.db
  (:require [com.stuartsierra.component :as component]
            [jeesql.autoreload :as autoreload]
            [environ.core :refer [env]])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource DataSources)))

(def db-url (env :database-url))
(def db-url-parts (rest (re-find #"(\w*)://(\w*):(\w*)@(\w*):(\w*)/(\w*)" db-url)))
(def db-protocol (nth db-url-parts 0))
(def db-username (nth db-url-parts 1))
(def db-password (nth db-url-parts 2))
(def db-host (nth db-url-parts 3))
(def db-port (nth db-url-parts 4))
(def db-database (nth db-url-parts 5))

(defrecord Db
           [dev-mode?]
  component/Lifecycle
  (start [this]
    (let [cpds (doto (ComboPooledDataSource.)
                 (.setDriverClass "org.postgresql.Driver")
                 (.setJdbcUrl (str "jdbc:" db-protocol "://" db-host ":" db-port "/" db-database))
                 (.setUser db-username)
                 (.setPassword db-password)
                 (.setMaxIdleTimeExcessConnections (* 30 60))
                 (.setMaxIdleTime (* 3 60 60)))]
      (when dev-mode?
        (autoreload/start-autoreload))
      (assoc this :datasource cpds)))
  (stop [{:keys [datasource] :as this}]
    (when dev-mode?
      (autoreload/stop-autoreload))
    (DataSources/destroy datasource)
    this))
