(defproject todo-repl-webapp "0.1.0-SNAPSHOT"
  :description "Todo-repl"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [org.ocpsoft.prettytime/prettytime-nlp "3.0.2.Final"]]
  :uberjar-name "todo-repl-webapp-standalone.jar"
  :main todo-repl-webapp.handler
  :source-paths ["src"
                 "src/todo_repl/"
                 "src/todo_repl_webapp/"]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler todo-repl-webapp.handler/secured-app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]
                        [com.cemerick/friend "0.2.0"]]}})
