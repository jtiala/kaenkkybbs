(ns forum.utils)

(defn format-key [k]
  (keyword (clojure.string/replace (name k) #"_" "-")))

(defn format-keys [d]
  (if (map? d)
    (into {}
          (map (fn [[k v]]
                 [(format-key k) (format-keys v)])
               d))
    (if (sequential? d)
      (map format-keys d)
      d)))

(defn format-response [data]
  "Formats data to be used as an API response"
  {:result (format-keys data)})

(format-response {:asd_asd 1})
