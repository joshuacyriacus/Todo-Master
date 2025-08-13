package app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.algorithms.BubbleSortStudentsByCgpa;
import app.algorithms.QuickSortStudentsByName;
import app.algorithms.SearchAlgorithms;
import app.model.Result;
import app.model.Student;

public class StudentService {
	private final List<Student> students;
	private final Map<String, Student> idToStudent;

	public StudentService() {
		this.students = new ArrayList<>();
		this.idToStudent = new HashMap<>();
	}

	public synchronized void addStudent(Student student) {
		if (student == null) throw new IllegalArgumentException("Student cannot be null");
		String id = normalizeId(student.getId());
		if (idToStudent.containsKey(id)) {
			throw new IllegalArgumentException("A student with this ID already exists");
		}
		students.add(student);
		idToStudent.put(id, student);
	}

	public synchronized boolean updateStudent(Student updated) {
		String id = normalizeId(updated.getId());
		Student existing = idToStudent.get(id);
		if (existing == null) return false;
		existing.setName(updated.getName());
		existing.clearResults();
		for (Result r : updated.getResults()) {
			existing.addResult(r);
		}
		return true;
	}

	public synchronized boolean deleteStudentById(String id) {
		String norm = normalizeId(id);
		Student existing = idToStudent.remove(norm);
		if (existing == null) return false;
		students.remove(existing);
		return true;
	}

	public synchronized List<Student> getAllStudents() {
		return new ArrayList<>(students);
	}

	public synchronized Student findByIdLinear(String id) {
		String needle = normalizeId(id);
		for (Student s : students) {
			if (normalizeId(s.getId()).equals(needle)) return s;
		}
		return null;
	}

	public synchronized Student findByIdBinary(String id) {
		List<Student> copy = new ArrayList<>(students);
		QuickSortStudentsByName.sortById(copy);
		return SearchAlgorithms.binarySearchById(copy, id);
	}

	public synchronized List<Student> searchByName(String nameFragment) {
		return SearchAlgorithms.linearSearchByName(students, nameFragment);
	}

	public synchronized List<Student> sortedByNameQuickSort() {
		List<Student> copy = new ArrayList<>(students);
		QuickSortStudentsByName.sortByName(copy);
		return copy;
	}

	public synchronized List<Student> sortedByCgpaBubbleSortDesc() {
		List<Student> copy = new ArrayList<>(students);
		BubbleSortStudentsByCgpa.sortByCgpaDescending(copy);
		return copy;
	}

	public synchronized double computeAverageCgpa() {
		if (students.isEmpty()) return 0.0;
		double total = 0.0;
		for (Student s : students) {
			total += s.calculateCgpa();
		}
		return total / students.size();
	}

	public synchronized Student findTopPerformer() {
		Student best = null;
		double bestCgpa = -1.0;
		for (Student s : students) {
			double cgpa = s.calculateCgpa();
			if (cgpa > bestCgpa) {
				bestCgpa = cgpa;
				best = s;
			}
		}
		return best;
	}

	private String normalizeId(String id) {
		return id == null ? "" : id.trim().toLowerCase();
	}
}