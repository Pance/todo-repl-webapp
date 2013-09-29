(ns todo-repl-webapp.handler
  (:gen-class :main true)
  (:use compojure.core
        hiccup.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.form :as form]
            [hiccup.page :as page]
            [ring.adapter.jetty :as jetty]
            [todo-repl.core :as todo]))

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

(def *tasks-ref* (ref []))
(defn tasks [] (deref *tasks-ref*))
(defn add-new-task [x]
  (dosync (alter *tasks-ref* #(cons 
                                (todo/new-task-better x)
                                %1))))
(defn add-to-tasks [x]
  (dosync (alter *tasks-ref* #(cons x %1))))

(defroutes app-routes
  (GET "/" [] (do (println "get /")
                  (home-page)))
  (POST "/eval" [evalInput]
    (if (nil? evalInput)
      (eval-page "nil")
      (do (println "/eval " evalInput)
          (binding [*ns* (find-ns 'todo-repl-webapp.handler)]
            (let [result (load-string evalInput)]
              (println "evals to: " result)
              (eval-page (str result)))))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app-routes {:port (Integer. port) :join? false}))
