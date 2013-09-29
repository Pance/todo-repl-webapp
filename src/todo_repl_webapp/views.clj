(ns todo-repl-webapp.views
  (:require [hiccup.form :as form]
            [hiccup.page :as page]
            [hiccup.core :as core]))

(defn home [& _]
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
                                          "blah")
                          [:br]
                          (form/submit-button {:id "todoSubmitButton"}
                                              "Eval"))]
          [:div#display.col-md-7.col-md-offset-2]
          (page/include-js "js/eval.js")]))

(defn eval [x & xs]
  (core/html [:h1 "Eval"]
        [:h2 x]))
