(ns todo-repl-webapp.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as credentials])
            [todo-repl-webapp.views :as views]
            [todo-repl-webapp.eval :as e]
            [todo-repl-webapp.users :as u]
            [todo-repl-webapp.web-repl :as web-repl]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/home" [] (views/home (web-repl/tasks)))
  (POST "/eval" [evalInput]
    (let [eval-result (e/evaluate evalInput)]
      (println "/eval " evalInput " evals to: " eval-result)
      (views/display eval-result)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(def secured-app
  (-> app
    (friend/authenticate {:credential-fn
                            (partial credentials/bcrypt-credential-fn u/users)
                          :workflows [(workflows/interactive-form)]})))
