(ns todo-repl-webapp.eval)

(defn evaluate [input-form]
  "Eval an input-form in the context of todo_repl_webapp/web_repl."
  (if (nil? input-form)
    "nil"
    (binding [*ns* (find-ns 'todo-repl-webapp.web-repl)]
      (let [result (load-string input-form)]
        (println "evals to: " result)
        result))))