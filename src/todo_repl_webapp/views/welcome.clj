(ns todo-repl-webapp.views.welcome
  (:require [todo-repl-webapp.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to todo-repl-webapp"]))
