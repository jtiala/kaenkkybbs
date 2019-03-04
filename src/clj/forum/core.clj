(ns forum.core
  (:require
   [forum.system :refer [create-system]]
   [com.stuartsierra.component :as component]
   [forum.migrations :as migrations :refer [migrate rollback]])
  (:gen-class))

(defn -main [& args]
  "Run migrations, create and start an instance of the system."
  (migrations/migrate)
  (defonce system (component/start (create-system))))
