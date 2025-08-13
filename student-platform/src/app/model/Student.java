package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Student extends Person {
	private final List<Result> results;

	public Student(String id, String name) {
		super(id, name);
		this.results = new ArrayList<>();
	}

	public List<Result> getResults() {
		return Collections.unmodifiableList(results);
	}

	public void clearResults() {
		this.results.clear();
	}

	public void addResult(Result result) {
		this.results.add(result);
	}

	public boolean removeResultByCourseCode(String courseCode) {
		Iterator<Result> it = results.iterator();
		while (it.hasNext()) {
			Result r = it.next();
			if (r.getCourse().getCode().equalsIgnoreCase(courseCode)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public int getTotalCredits() {
		int total = 0;
		for (Result r : results) {
			total += r.getCourse().getCreditUnits();
		}
		return total;
	}

	public double calculateCgpa() {
		double totalWeightedPoints = 0.0;
		int totalCredits = 0;
		for (Result r : results) {
			int cu = r.getCourse().getCreditUnits();
			totalWeightedPoints += r.getGradePoint() * cu;
			totalCredits += cu;
		}
		if (totalCredits == 0) return 0.0;
		return totalWeightedPoints / totalCredits;
	}
}