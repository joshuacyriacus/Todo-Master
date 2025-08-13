package app.model;

import java.util.Objects;

public class Course {
	private final String code;
	private final String title;
	private final int creditUnits;

	public Course(String code, String title, int creditUnits) {
		if (code == null || code.isBlank()) throw new IllegalArgumentException("Course code is required");
		if (title == null || title.isBlank()) throw new IllegalArgumentException("Course title is required");
		if (creditUnits <= 0) throw new IllegalArgumentException("Credit units must be positive");
		this.code = code.trim().toUpperCase();
		this.title = title.trim();
		this.creditUnits = creditUnits;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public int getCreditUnits() {
		return creditUnits;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return Objects.equals(code, course.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		return code + " - " + title + " (" + creditUnits + "U)";
	}
}