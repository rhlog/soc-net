(defproject soc-net "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :dependencies [
                 [org.clojure/clojure       "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [ring/ring-core            "1.7.1"]
                 [clj-http                  "3.9.1"]
                 [rum                       "0.11.3"]
                 [org.immutant/immutant     "2.1.10"]
                 [compojure                 "1.6.1"]
                 [org.clojure/data.json     "0.2.6"]
                 [clj-time                  "0.15.0"]
                 ]

  :plugins [[lein-ring "0.12.5"]
            [lein-cljfmt "0.6.4"]]

  :main soc-net.server
  :repl-options {:init-ns soc-net.server})
