package app.algorithms;

import java.util.ArrayList;
import java.util.List;

import app.model.Student;

public class SearchAlgorithms {
	public static Student binarySearchById(List<Student> studentsSortedById, String id) {
		int low = 0;
		int high = studentsSortedById.size() - 1;
		String target = id.toLowerCase();
		while (low <= high) {
			int mid = (low + high) >>> 1;
			String midId = studentsSortedById.get(mid).getId().toLowerCase();
			int cmp = midId.compareTo(target);
			if (cmp == 0) return studentsSortedById.get(mid);
			if (cmp < 0) low = mid + 1; else high = mid - 1;
		}
		return null;
	}

	public static List<Student> linearSearchByName(List<Student> students, String nameFragment) {
		List<Student> matches = new ArrayList<>();
		String needle = nameFragment.toLowerCase();
		for (Student s : students) {
			if (s.getName().toLowerCase().contains(needle)) {
				matches.add(s);
			}
		}
		return matches;
	}
}