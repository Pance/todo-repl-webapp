# todo-repl-webapp

A web application that provides a repl that lets you manage todos.
The app can be seen at: http://protected-mesa-6456.herokuapp.com/

## Usage

1. Install [Leiningen](https://github.com/technomancy/leiningen)

2. Clone the repo
```
git clone git@github.com:Pance/todo-repl-webapp.git
cd todo-repl-webapp
```

3. Start the app on a server listening on port 8000
```
lein run 8000
```
Then, point your browser at: [http://localhost:8000](http://localhost:8000)

4. *Or*, start an interactive development server on port 3000
```
lein ring server-headless
```
Your browser will open and load the URL to your local-running app

## How to use Todo-Repl

To Display Tasks
```
(tasks)
```

To Reset Tasks
```
(init-tasks)
```

Tasks look like this
```
{:name name
 :due date (try natural language for the date)
 :context context}
```

To Add a Task
```
(add-new-task
  {:name "Build a Rocketship"
   :due "tomorrow"
   :context "hobbies"}
```

To Complete a Task
```
(complete-task index)
```
