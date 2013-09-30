(ns todo-repl-webapp.views
  (:require [hiccup.form :as form]
            [hiccup.page :as page]
            [hiccup.core :as core]))

(def instructions
  (core/html
    [:div.alert.alert-info.col-md-7.col-md-offset-2
      [:div [:b "Instructions"]]
      [:br]
      [:div.col-md-4
        [:p "To Display Tasks"]
        [:code "(tasks)"]]
      [:div.col-md-4
        [:p "To Reset Tasks"]
        [:code "(init-tasks)"]]
      [:div.col-md-4
        [:p "Tasks look like this"]
        [:code "{:name name" [:br]
               ":due date " [:em "(try natural language for the date)"] [:br]
               ":context context}"]]
      [:div.col-md-4
        [:p "To Add a Task"]
        [:code "(add-new-task" [:br]
               "{:name \"Build a Rocketship\"" [:br]
               "               :due \"tomorrow\"" [:br]
               "               :context \"hobbies\"}"]]

      [:div.col-md-4
        [:p "To Complete a Task"]
        [:code "(complete-task " 
               [:em "index"]
               ")"]]]))

(defn home [tasks]
  (core/html [:head 
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
                                          "(tasks)")
                          [:br]
                          (form/submit-button {:id "todoSubmitButton"}
                                              "Eval"))]
          instructions
          [:div#display.col-md-7.col-md-offset-2
            (display tasks)]
          (page/include-js "js/eval.js")]))

(defn task-item
  "Given a task (map), return a div representing the task"
  [task-map]
  (core/html
      [:div.col-md-7.col-md-offset-1
        (if (= :complete (:status task-map))
            [:p.lead.text-muted [:strike (:name task-map)]]
            [:p.lead (:name task-map)])]
      [:div.col-md-2
        (if-not (nil? (:context task-map))
          [:p.text-muted (:context task-map)])]
      [:div.col-md-2
        (if-not (nil? (:due task-map))
          [:p.text-info (:due task-map)])]))

(defn task-line-item
  "When given a single task (map), return a html table line representing the task"
  [task-map]
  (core/html [:div.panel.panel-default
               [:div.panel-body
                 (task-item task-map)]]))

(defn task-table 
  "When given a vector of tasks (maps), return an html table representing those tasks"
  [task-vec]
  (core/html
      (clojure.string/join
        (map task-line-item task-vec))))

(defn display [x & xs]
  (if-not (empty? x)
    (core/html [:h2 "list"]
          #_[:h3 x]
            (task-table (vec x)))))
