(ns todo-repl-webapp.handler
  (:gen-class :main true)
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as credentials])
            [todo-repl.core :as todo]
            [todo-repl-webapp.views :as views]))

(def users {"root" {:username "root"
                   :password (credentials/hash-bcrypt "admin-pass")
                   :roles #{::admin}}
            "foo" {:username "foo"
                    :password (credentials/hash-bcrypt "foo-pass")
                    :roles #{::user}}})

(def ^:dynamic *tasks-ref* (ref []))
(defn tasks [] (deref *tasks-ref*))
(defn add-new-task [x]
  (dosync (alter *tasks-ref* #(conj
                                %1
                                (todo/new-task-better x)))))
(defn complete-task [index]
  "Sets the :status of task at index to :complete"
  (dosync (alter *tasks-ref* assoc-in [index :status] :complete)))

;; Initialize some tasks
(defn init-tasks [& _]
  (do
    (dosync (ref-set *tasks-ref*  []))
    (add-new-task {:name "Go get some milk"
                   :context "shopping"
                   :due "tomorrow"})
    (add-new-task {:name "Go get new shoes"
                   :context "shopping"
                   :due "friday"})
    (add-new-task {:name "Clean the kitchen sink"
                   :context "cleaning"})))
(init-tasks)

(defroutes app-routes
  (GET "/" [] "todo repl landing page")
  (GET "/home" [] (do (println "get /")
                  (views/home (tasks))))
  (POST "/eval" [evalInput]
    (do 
      (println "/eval " evalInput)
      (if (nil? evalInput)
        (views/display "nil")
          (binding [*ns* (find-ns 'todo-repl-webapp.handler)]
            (let [result (load-string evalInput)]
              (println "evals to: " result)
              (views/display result))))))
  (GET "/admin" request (friend/authorize #{::admin}
                                          "Admin page"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(def secured-app
  (-> app
    (friend/authenticate {:credential-fn
                            (partial credentials/bcrypt-credential-fn users)
                          :workflows [(workflows/interactive-form)]})))

(defn -main [port]
  (jetty/run-jetty
    secured-app
    {:port (Integer. port) :join? false}))
