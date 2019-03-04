(ns ^:figwheel-hooks forum.core
  (:require [goog.dom :as gdom]
            [ajax.core :refer [GET POST json-response-format]]
            [reagent.core :as reagent :refer [atom]])
  (:require-macros [reagent.ratom :refer [reaction]]))

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:title "Forum" :posts [] :show-posts true}))

(def get-post-count
  (reaction (count (:posts @app-state))))

(defn toggle-posts []
  (swap! app-state #(assoc % :show-posts (not (:show-posts @app-state)))))

(defn report-ajax-error [error]
  (js/window.alert "Ajax error! " error))

(defn- create-params [params handler]
  {:params        params
   :handler       (fn [response]
                    (def my-val response)
                    (swap! app-state #(handler % (:result response))))
   :keyword?      true
   :error-handler report-ajax-error})

(defn api-post [path params handler]
  (POST (str "/api" path) (create-params params handler)))

(defn api-get [path params handler]
  (GET (str "/api" path) (create-params params handler)))

(defn initial-populate-state []
  (api-get "/posts" {} (fn [state result]
                         (assoc state :posts result))))

(defn header []
  [:header
   [:h1 (:title @app-state)]])

(defn toggle-posts-button []
  [:button {:on-click #((toggle-posts))}
   (if (:show-posts @app-state) "Hide posts" "Show posts")])

(defn posts []
  [:div {:class "posts"}
   [:h2 (str "Posts: " @get-post-count)]
   (toggle-posts-button)
   (if (:show-posts @app-state)
     [:ul
      (for [post (:posts @app-state)]
        ^{:key (str "post-" (:id post))}
        [:li
         [:a {:href "/"} (str (:id post) ": " (:title post))]])])])

(defn layout []
  [:div {:id "layout"}
   (header)
   (posts)])

(defn mount [el]
  (reagent/render-component [layout] el)
  (initial-populate-state))

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
