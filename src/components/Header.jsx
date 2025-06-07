
export function Header({ todos }) {
    const todosLength = todos.length;
    const Istaskplural  = todosLength != 1 ? 'tasks':'task';
    return (
        <header>
            <h1 className="text-gradient">You have {todosLength} open {Istaskplural}</h1>
        </header>
    )
}