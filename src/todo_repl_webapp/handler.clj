(ns todo-repl-webapp.handler
  (:gen-class :main true)
  (:use compojure.core
        hiccup.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.form :as form]
            [ring.adapter.jetty :as jetty]))

(defn home-page [& x]
  (html [:head [:title "todo-repl"]]
        [:body [:h1 "todo-repl"]
               (form/form-to [:post "/eval"]
                        (form/text-area "evalInput" "blah")
                        [:br]
                        (form/submit-button "eval"))]))
(defn eval-page [& x]
  (html [:h1 "Eval"]
        [:h2 (first x)]))

(defroutes app-routes
  (GET "/" [] (home-page))
  (POST "/eval" [evalInput] (eval-page evalInput))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app-routes {:port (Integer. port) :join? false}))
