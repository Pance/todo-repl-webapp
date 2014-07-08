(ns todo-repl-webapp.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [todo-repl-webapp.views :as views]
            [todo-repl-webapp.eval :as e]
            [todo-repl-webapp.web-repl :as web-repl]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/home" [] (views/home (tasks)))
  (POST "/eval" [evalInput]
    (let [eval-result (e/evaluate evalInput)]
      (println "/eval " evalInput " evals to: " eval-result)
      (views/display eval-result)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
