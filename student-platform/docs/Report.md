# Smart Student Platform – Brief Report

## Project objectives
- Build a Java Swing desktop app to manage student records (ID, name, course results).
- Demonstrate OOP (abstraction, encapsulation, inheritance, interfaces).
- Implement custom algorithms: QuickSort, BubbleSort, linear and binary search.
- Provide GUI with event handling and robust exception handling.
- Present summaries: class average CGPA and top performer.

## Architecture & class diagram (textual)
- `app.model`
  - `Identifiable` (interface): `getId()`
  - `Person` (abstract): fields `id`, `name`
  - `Student` (extends `Person`): list of `Result`, methods `calculateCgpa`, etc.
  - `Course`: `code`, `title`, `creditUnits`
  - `Result`: `course`, `score`, `getGradePoint()`, `getLetterGrade()`
- `app.algorithms`
  - `QuickSortStudentsByName`: QuickSort by name and by ID
  - `BubbleSortStudentsByCgpa`: BubbleSort by CGPA (desc)
  - `SearchAlgorithms`: binary search by ID; linear search by name
- `app.service`
  - `StudentService`: in-memory repository + operations (add/update/delete/search/sort/summaries)
- `app.ui`
  - `StudentManagementApp` (JFrame): tabs for Add/Update, Search & View, Summaries
- `app.Main`: entry point.

Data flow: UI -> `StudentService` -> operates on in-memory list of `Student` containing `Result` of `Course`. Algorithms are invoked from the service.

## Key implementation highlights
- CGPA on a 5-point scale using weighted average by credit units.
- Custom QuickSort for `List<Student>` by `name` and `id` to support binary search precondition.
- BubbleSort by computed CGPA to satisfy requirement for a second custom sort.
- Exception handling via validation and `JOptionPane` error dialogs; guard clauses across constructors.
- Event-driven UI: action listeners on buttons; double-click row to edit.

## Lessons learned
- Keeping algorithms generic but tailored to domain types improves clarity.
- Separating service layer from UI eases testing and future replacement of storage.
- Early validation with clear error messages greatly improves UX.