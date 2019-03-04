(ns forum.system
  (:require
   [com.stuartsierra.component :as component]
   [forum
    [db :as db]
    [http-server :as server]]
   [forum.services.posts :as posts]
   [forum.migrations :as migrations :refer [migrate rollback]]))

(def ^{:private true} config
  (-> "config.edn" slurp read-string))

(defn create-system
  "Constructs a system map."
  []
  (component/system-map
   :db (db/->Db (:db config))
   :http-server (server/create-server (:http-server config))
   :posts (component/using
           (posts/->Post)
           [:db :http-server])))
