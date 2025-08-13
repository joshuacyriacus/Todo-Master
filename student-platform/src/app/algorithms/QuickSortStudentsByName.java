package app.algorithms;

import java.util.List;

import app.model.Student;

public class QuickSortStudentsByName {
	public static void sortByName(List<Student> students) {
		quickSortByName(students, 0, students.size() - 1);
	}

	public static void sortById(List<Student> students) {
		quickSortById(students, 0, students.size() - 1);
	}

	private static void quickSortByName(List<Student> list, int low, int high) {
		if (low < high) {
			int p = partitionByName(list, low, high);
			quickSortByName(list, low, p - 1);
			quickSortByName(list, p + 1, high);
		}
	}

	private static void quickSortById(List<Student> list, int low, int high) {
		if (low < high) {
			int p = partitionById(list, low, high);
			quickSortById(list, low, p - 1);
			quickSortById(list, p + 1, high);
		}
	}

	private static int partitionByName(List<Student> list, int low, int high) {
		String pivot = list.get(high).getName().toLowerCase();
		int i = low - 1;
		for (int j = low; j < high; j++) {
			String current = list.get(j).getName().toLowerCase();
			if (current.compareTo(pivot) <= 0) {
				i++;
				swap(list, i, j);
			}
		}
		swap(list, i + 1, high);
		return i + 1;
	}

	private static int partitionById(List<Student> list, int low, int high) {
		String pivot = list.get(high).getId().toLowerCase();
		int i = low - 1;
		for (int j = low; j < high; j++) {
			String current = list.get(j).getId().toLowerCase();
			if (current.compareTo(pivot) <= 0) {
				i++;
				swap(list, i, j);
			}
		}
		swap(list, i + 1, high);
		return i + 1;
	}

	private static void swap(List<Student> list, int i, int j) {
		if (i == j) return;
		Student tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, tmp);
	}
}