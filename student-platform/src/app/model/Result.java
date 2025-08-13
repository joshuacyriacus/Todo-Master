package app.model;

public class Result {
	private final Course course;
	private final double score; // 0-100

	public Result(Course course, double score) {
		if (course == null) throw new IllegalArgumentException("Course cannot be null");
		if (score < 0 || score > 100) throw new IllegalArgumentException("Score must be between 0 and 100");
		this.course = course;
		this.score = score;
	}

	public Course getCourse() {
		return course;
	}

	public double getScore() {
		return score;
	}

	public double getGradePoint() {
		// 5-point scale mapping
		if (score >= 70) return 5.0;
		if (score >= 60) return 4.0;
		if (score >= 50) return 3.0;
		if (score >= 45) return 2.0;
		if (score >= 40) return 1.0;
		return 0.0;
	}

	public String getLetterGrade() {
		if (score >= 70) return "A";
		if (score >= 60) return "B";
		if (score >= 50) return "C";
		if (score >= 45) return "D";
		if (score >= 40) return "E";
		return "F";
	}
}