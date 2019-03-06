(defproject forum "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "https://example.com/FIXME"
  :license {:name "FIXME: Choose a license"
            :url "https://choosealicense.com/"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.4.490"]
                 [com.mchange/c3p0 "0.9.5.3"]
                 [org.postgresql/postgresql "42.2.5"]
                 [ragtime "0.8.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [webjure/jeesql "0.4.7"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [ring "1.7.0"]
                 [com.cognitect/transit-cljs "0.8.256"]
                 [com.cognitect/transit-clj "0.8.313"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.8.1"]
                 [cljs-ajax "0.8.0"]
                 [clj-commons/secretary "1.2.4"]]

  :source-paths ["src/clj" "src/cljc" "src/cljs"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" forum.test-runner]}

  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component.repl "0.2.0"]
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :source-paths ["dev" "test/clj" "test/cljs"]
                   :main user}
             :test {:source-paths ["test/clj" "test/cljs"]}}

  :clean-targets ^{:protect false} ["resources/public/cljs-out" "target"]

  :main forum.core
  :aot [forum.core])
