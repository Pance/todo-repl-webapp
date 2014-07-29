(ns todo-repl-webapp.handler
  (:require [compojure.core :refer (GET POST defroutes)]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as credentials])
            [todo-repl-webapp.views :as views]
            [todo-repl-webapp.eval :as e]
            [todo-repl-webapp.users :refer (users)]
            [todo-repl-webapp.web-repl :as web-repl]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/home" [] (views/home (web-repl/tasks)))
  (POST "/eval" [evalInput]
    (let [eval-result (e/evaluate evalInput)]
      (println "/eval " evalInput " evals to: " eval-result)
      (views/display eval-result)))
  (GET "/admin" request (friend/authorize #{::admin}
                                          "Admin page"))
  (GET "/login" [] (views/login-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def secured-app
    (handler/site
      (friend/authenticate
            app-routes
            {:login-uri "/login"
             :credential-fn
               #(credentials/bcrypt-credential-fn users %)
             :workflows [(workflows/interactive-form)]})))
