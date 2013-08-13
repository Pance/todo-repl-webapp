(ns todo-repl-webapp.handler
  (:use compojure.core
        hiccup.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.form :as form]))

(defn home-page [& x]
  (html [:head [:title "todo-repl"]]
        [:body [:h1 "todo-repl"]
               (form/form-to [:put "/eval"]
                        (form/text-area [] "blah")
                        [:br]
                        (form/submit-button "eval"))]))
(defn eval-page [& x]
  (html [:h1 x]))

(defroutes app-routes
  (GET "/" [] (home-page))
  (PUT "/eval/:x" [x] (eval-page x))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
