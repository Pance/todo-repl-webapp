(ns todo-repl-webapp.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [todo-repl-webapp.views :as views]))

(def ^:dynamic *tasks-ref* (ref []))
(defn tasks [] (deref *tasks-ref*))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/home" [] (views/home (tasks)))
  (POST "/eval" [evalInput]
    (do 
      (println "/eval " evalInput)
      (if (nil? evalInput)
        (views/display "nil")
          (binding [*ns* (find-ns 'todo-repl-webapp.web-repl)]
            (let [result (load-string evalInput)]
              (println "evals to: " result)
              (views/display result))))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
