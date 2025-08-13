package app.algorithms;

import java.util.List;

import app.model.Student;

public class BubbleSortStudentsByCgpa {
	public static void sortByCgpaDescending(List<Student> students) {
		int n = students.size();
		boolean swapped;
		do {
			swapped = false;
			for (int i = 1; i < n; i++) {
				double prevCgpa = students.get(i - 1).calculateCgpa();
				double currCgpa = students.get(i).calculateCgpa();
				if (prevCgpa < currCgpa) {
					Student tmp = students.get(i - 1);
					students.set(i - 1, students.get(i));
					students.set(i, tmp);
					swapped = true;
				}
			}
			n--;
		} while (swapped);
	}
}