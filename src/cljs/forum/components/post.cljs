(ns forum.components.post)

(defn component [post]
  [:div {:class "post"}
   [:p (:message post)]])
