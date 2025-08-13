# User Manual

## Launching
- Using scripts: run `./build.sh` to compile and start the app.
- Using JAR: run `java -jar dist/student-platform.jar` (after running `./package.sh`).

## Add a new student
1. Open the "Add/Update" tab.
2. Enter Student ID and Full Name.
3. For each course:
   - Enter Course Code, Title, Credit Units, and Score (0–100).
   - Click "Add Course to List".
4. Click "Save New Student".

## Update an existing student
- You can either double-click a student in the "Search & View" table to load into the editor, modify fields and courses, then click "Update Existing Student"; or manually enter the same Student ID, add courses, and click "Update".

## Search and view
- By ID: enter an ID and click "Binary Search".
- By Name: enter a name fragment and click "Linear Search".
- Click "Show All" to list all students.
- Double-click a student row to load into the editor.

## Sorting
- Click "Sort by Name (QuickSort)" to sort alphabetically by name.
- Click "Sort by CGPA (BubbleSort)" to sort by CGPA descending.

## Summaries
- Go to the "Summaries" tab and press "Refresh" to see total students, class average CGPA, and top performer.

## Error messages
- Invalid inputs (non‑numeric scores/units, missing fields, scores out of range) are shown using dialog popups.