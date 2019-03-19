(ns forum.components.footer)

(defn component []
  [:footer {:id "footer"}
   [:span {:class "logo"} "🍕"]
   [:span {:class "copyright"} "© KaenkkyBBS 2019."]])
