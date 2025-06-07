import { Header } from "./components/Header"
import { Tabs } from "./components/Tabs"
import { TodoList } from "./components/Todolist"
import { TodoInput } from "./components/Todoinput"
import { useState, useEffect } from "react"


function App() {
 
 // const todos = [
  //  { input: 'Hello! Add your first todo!', complete: true},
  //  { input: 'Get the grocories!', complete: false },
  //  { input: 'Learn how to web design', complete: false },
  //  { input: 'Say hi to gran gran', complete: true },
  //]

  const [todos, setTodos] = useState([
    { input: 'Hello! Add your first todo!', complete: false},
  ])

   const [selectedTab, setSelectedTab] = useState('Open');

  function handleAddTodos(newTodo) {
     const newTodoList = [...todos, { input: newTodo, complete: false }];
     setTodos(newTodoList);
     handleSaveData(newTodoList);
  }

  function handleCompletedTodos(index) {
    // update/edit/modify

    let newTodoList = [...todos]
    let CompletedTodo = todos[index]
    CompletedTodo['complete'] = true
    newTodoList[index] = CompletedTodo;
    setTodos(newTodoList);
    handleSaveData(newTodoList);
  }

  function handleDeleteTodos(index) {
    const newTodoList = todos.filter((val, valindex) => {
      return valindex !== index
    })
    setTodos(newTodoList);
    handleSaveData(newTodoList);
  }

  function handleSaveData(currTodos) {
    localStorage.setItem('todo-App', JSON.stringify({ todos: currTodos }));
  }


   useEffect(() => {
    if (!localStorage || !localStorage.getItem('todo-App')) {
      return
    }
    let db = JSON.parse(localStorage.getItem('todo-App'));
    setTodos(db.todos)
   },[])
  return (
    <>
     <Header  todos={todos}/>
     <Tabs  todos={todos} 
     selectedTab={selectedTab} 
     setSelectedTab={setSelectedTab}/>
     <TodoList
      handleDeleteTodos={handleDeleteTodos} 
      selectedTab={selectedTab} 
       todos={todos}
       handleCompletedTodos={handleCompletedTodos}
       />
     <TodoInput  handleAddTodos={handleAddTodos}/>
    </>
  )
}

export default App
