(ns forum.components.footer)

(defn component []
  [:footer#footer
   [:span.logo "🍕"]
   [:span.copyright "© KaenkkyBBS 2019."]
   [:span.links
    [:a {:href "https://github.com/jtiala/kaenkkybbs" :target "_blank"} "GitHub"]]])
