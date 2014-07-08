(ns todo-repl-webapp.users
  (cemerick.friend [credentials :as credentials])

(def users {"root" {:username "root"
                    :password (credentials/hash-bcrypt "admin-pass")
                    :roles #{::admin}}
            "foo"  {:username "foo"
                    :password (credentials/hash-bcrypt "foo-pass")
                    :roles #{::user}}})
