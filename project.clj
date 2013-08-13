(defproject todo-repl-webapp "0.1.0-SNAPSHOT"
  :description "Todo-repl"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler todo-repl-webapp.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
