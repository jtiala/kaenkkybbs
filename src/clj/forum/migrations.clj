(ns forum.migrations
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(def ^{:private true} config
  (-> "config.edn" slurp read-string))

(defn load-config [url]
  {:datastore (jdbc/sql-database url)
   :migrations (jdbc/load-resources "migrations")})

(def migrate #(repl/migrate (load-config (get-in config [:db :url]))))
(def rollback #(repl/rollback (load-config (get-in config [:db :url]))))
