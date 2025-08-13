# Smart Student Platform (Java Swing)

A simple Java Swing desktop app for managing students, courses, and results. Demonstrates OOP, custom sorting (QuickSort, BubbleSort), and searching (linear, binary), plus GUI event handling and exception handling.

## Run

```bash
# Compile
javac -d out $(find src -name "*.java")

# Run
java -cp out app.Main
```

## Features
- Add/update students with multiple course results
- Sorting: QuickSort by name, BubbleSort by CGPA
- Searching: Binary by ID, Linear by name
- Summaries: class average CGPA, top performer
- Errors surfaced via JOptionPane dialogs