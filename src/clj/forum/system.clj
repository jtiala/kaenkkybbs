(ns forum.system
  (:require
   [com.stuartsierra.component :as component]
   [forum
    [db :as db]
    [http-server :as server]]
   [forum.services.users :as users]
   [forum.services.threads :as threads]
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
   :users (component/using
           (users/->Users)
           [:db :http-server])
   :threads (component/using
             (threads/->Threads)
             [:db :http-server])
   :posts (component/using
           (posts/->Posts)
           [:db :http-server])))

