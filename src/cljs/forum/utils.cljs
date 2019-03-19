(ns forum.utils)

(defn parse-int [s]
  (js/parseInt s))

(defn left-pad [s c n]
  (loop [res (str s)]
    (if (< (count res) n)
      (recur (str c res))
      res)))

(defn format-timestamp [ts]
  (str (left-pad (.getDate ts) "0" 2) "."
       (left-pad (inc (.getMonth ts)) "0" 2) "."
       (.getFullYear ts) " "
       (.getHours ts) ":"
       (left-pad (.getMinutes ts) "0" 2) ":"
       (left-pad (.getSeconds ts) "0" 2)))
