(ns forum.components.badge)

(defn component [role]
  (if (or (= role "admin") (= role "moderator"))
    [:span.badge role]))
