(ns forum.migrations
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]
            [environ.core :refer [env]]))

(def database-url
  (env :database-url))

(defn load-config [url]
  {:datastore (jdbc/sql-database (str "jdbc:" url))
   :migrations (jdbc/load-resources "migrations")})

(def migrate #(repl/migrate (load-config database-url)))
(def rollback #(repl/rollback (load-config database-url)))
