(defproject todo-repl-webapp "0.1.0-SNAPSHOT"
  :description "Todo-repl"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [hiccup "1.0.4"]
                 [org.ocpsoft.prettytime/prettytime-nlp "3.0.2.Final"]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler todo-repl-webapp.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
