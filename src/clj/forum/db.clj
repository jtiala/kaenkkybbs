(ns forum.db
  (:require [com.stuartsierra.component :as component])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource DataSources)))

(defrecord Db
           [config]
  component/Lifecycle
  (start [this]
    (let [cpds (doto (ComboPooledDataSource.)
                 (.setDriverClass (:classname config))
                 (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
                 (.setUser (:user config))
                 (.setPassword (:password config))
                 (.setMaxIdleTimeExcessConnections (* 30 60))
                 (.setMaxIdleTime (* 3 60 60)))]
      (assoc this :datasource cpds)))
  (stop [{:keys [datasource] :as this}]
    (DataSources/destroy datasource)
    this))
