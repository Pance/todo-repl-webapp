(ns todo-repl-webapp.handler
  (:gen-class :main true)
  (:use compojure.core
        hiccup.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.form :as form]
            [hiccup.page :as page]
            [ring.adapter.jetty :as jetty]))

(defn home-page [& _]
  (html [:head 
          [:title "todo-repl"]
          (page/include-css "css/bootstrap.min.css")
          (page/include-js "http://code.jquery.com/jquery-1.10.1.min.js"
                           "js/bootstrap.min.js")]
        [:body 
          [:div.col-md-7.col-md-offset-2
            [:h1 "todo-repl"]
            (form/form-to {:id "todoForm"}
                          [:post "/eval"]
                          (form/text-area {:class "form-control"}
                                          "evalInput" 
                                          "blah")
                          [:br]
                          (form/submit-button {:id "todoSubmitButton"}
                                              "Eval"))]
          [:div#display.col-md-7.col-md-offset-2]
          (page/include-js "js/eval.js")]))
(defn eval-page [x & xs]
  (html [:h1 "Eval"]
        [:h2 x]))

(defroutes app-routes
  (GET "/" [] (home-page))
  (POST "/eval" [evalInput] (eval-page evalInput))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app-routes {:port (Integer. port) :join? false}))
