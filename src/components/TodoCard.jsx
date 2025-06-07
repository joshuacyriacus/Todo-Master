

export function TodoCard(props) {
    const { todo, handleDeleteTodos, todoindex, handleCompletedTodos } = props;

    console.log(todo)
    return (
        <div className="card todo-item">
            {todo.input}
            <div className="todo-buttons" >
                <button onClick={() => {
                    handleCompletedTodos(todoindex);
                }} disabled={todo.complete}>
                     <h6>Done</h6>
                </button>
                <button onClick={() => {
                    handleDeleteTodos(todoindex);
                }}>
                    <h6>Delete</h6>
                </button>
            </div>
        </div>
    )
}