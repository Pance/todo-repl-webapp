(ns todo-repl.date-parsing
    (:import (org.ocpsoft.prettytime.nlp PrettyTimeParser)))

(defn nl-to-date [nl]
  (if (= nl nil)
      nil
      (if (= nl "never")
          "never"
          (..
            (new PrettyTimeParser)
            (parse nl)
            (get 0)))))
