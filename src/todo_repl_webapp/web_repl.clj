(ns todo-repl-webapp.web-repl
  (:require [todo-repl.core :as todo]))

(def ^:dynamic *tasks-ref* (ref []))

(defn tasks [] (deref *tasks-ref*))

(defn add-new-task [x]
  (dosync (alter *tasks-ref* #(conj
                                %1
                                (todo/new-task-better x)))))

(defn complete-task [index]
  "Sets the :status of task at index to :complete"
  (dosync (alter *tasks-ref* assoc-in [index :status] :complete)))

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
