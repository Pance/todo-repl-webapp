(ns todo-repl.core
    #_(:load "/todo-repl/date-parsing")
    (:require [todo-repl.date-parsing :as dp])
    #_(:use todo-repl.date-parsing))

(defn new-task [name due-by due-after context url]
  {:name name
   :due-after due-after
   :due-by due-by
   :context context
   :url url
   :status :incomplete
   :created (str (java.util.Date.))
   })

(defn new-task-better [{:keys [name due-after due-by context url]
                   :or {due-by "never"
                        due-after nil
                        context nil
                        url [{:label "" :url ""}] }}]
  (new-task name
            (dp/nl-to-date due-by)
            (dp/nl-to-date due-after)
            context
            url))

(defn add-new-task-to [new-task tasks]
  (cons (new-task-better new-task) tasks))

(defn filter-tasks [filter-attrib val tasks]
  (filter #(= val (filter-attrib %)) tasks))

(defn complete-task [& xs]
  (map #(assoc % :status :complete)) xs)
