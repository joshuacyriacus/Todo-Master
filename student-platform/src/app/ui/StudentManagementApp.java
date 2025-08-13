package app.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import app.model.Course;
import app.model.Result;
import app.model.Student;
import app.service.StudentService;

public class StudentManagementApp extends JFrame {
	private final StudentService studentService;

	// Add/Update tab components
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtCourseCode;
	private JTextField txtCourseTitle;
	private JTextField txtCreditUnits;
	private JTextField txtScore;
	private DefaultTableModel courseTableModel;
	private JTable tblCourses;

	// Search/View tab components
	private JTextField txtSearchId;
	private JTextField txtSearchName;
	private DefaultTableModel studentsTableModel;
	private JTable tblStudents;

	// Summary tab components
	private JLabel lblStudentCount;
	private JLabel lblAverageCgpa;
	private JLabel lblTopPerformer;

	public StudentManagementApp() {
		super("Smart Student Platform - Swing");
		this.studentService = new StudentService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 650));
		setLayout(new BorderLayout());

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Add/Update", createAddUpdatePanel());
		tabs.addTab("Search & View", createSearchViewPanel());
		tabs.addTab("Summaries", createSummaryPanel());

		add(tabs, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createAddUpdatePanel() {
		JPanel root = new JPanel(new BorderLayout());
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel form = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(5, 5, 5, 5);
		gc.fill = GridBagConstraints.HORIZONTAL;

		int r = 0;
		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Student ID:"), gc);
		txtId = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtId, gc);

		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Full Name:"), gc);
		txtName = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtName, gc);

		// Course entry row
		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Course Code:"), gc);
		txtCourseCode = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtCourseCode, gc);

		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Course Title:"), gc);
		txtCourseTitle = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtCourseTitle, gc);

		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Credit Units:"), gc);
		txtCreditUnits = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtCreditUnits, gc);

		gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Score (0-100):"), gc);
		txtScore = new JTextField();
		gc.gridx = 1; gc.gridy = r++; form.add(txtScore, gc);

		JButton btnAddCourse = new JButton("Add Course to List");
		btnAddCourse.addActionListener(this::onAddCourse);
		gc.gridx = 1; gc.gridy = r++; form.add(btnAddCourse, gc);

		root.add(form, BorderLayout.NORTH);

		courseTableModel = new DefaultTableModel(new Object[] { "Code", "Title", "Units", "Score", "Grade Pt" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tblCourses = new JTable(courseTableModel);
		JScrollPane courseScroll = new JScrollPane(tblCourses);
		courseScroll.setPreferredSize(new Dimension(800, 200));
		courseScroll.setBorder(BorderFactory.createTitledBorder("Pending Courses for Student"));
		root.add(courseScroll, BorderLayout.CENTER);

		JPanel actions = new JPanel();
		JButton btnRemoveCourse = new JButton("Remove Selected Course");
		btnRemoveCourse.addActionListener(e -> onRemoveCourse());
		JButton btnSave = new JButton("Save New Student");
		btnSave.addActionListener(e -> onSaveStudent());
		JButton btnUpdate = new JButton("Update Existing Student");
		btnUpdate.addActionListener(e -> onUpdateStudent());
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(e -> clearAddForm());
		actions.add(btnRemoveCourse);
		actions.add(btnSave);
		actions.add(btnUpdate);
		actions.add(btnClear);
		root.add(actions, BorderLayout.SOUTH);

		return root;
	}

	private JPanel createSearchViewPanel() {
		JPanel root = new JPanel(new BorderLayout());
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel search = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(5, 5, 5, 5);
		gc.fill = GridBagConstraints.HORIZONTAL;

		int r = 0;
		gc.gridx = 0; gc.gridy = r; search.add(new JLabel("Search by ID:"), gc);
		txtSearchId = new JTextField();
		gc.gridx = 1; gc.gridy = r; search.add(txtSearchId, gc);
		JButton btnSearchId = new JButton("Binary Search");
		btnSearchId.addActionListener(e -> onSearchById());
		gc.gridx = 2; gc.gridy = r++; search.add(btnSearchId, gc);

		gc.gridx = 0; gc.gridy = r; search.add(new JLabel("Search by Name:"), gc);
		txtSearchName = new JTextField();
		gc.gridx = 1; gc.gridy = r; search.add(txtSearchName, gc);
		JButton btnSearchName = new JButton("Linear Search");
		btnSearchName.addActionListener(e -> onSearchByName());
		gc.gridx = 2; gc.gridy = r++; search.add(btnSearchName, gc);

		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(e -> refreshStudentsTable(studentService.getAllStudents()));
		gc.gridx = 1; gc.gridy = r++; search.add(btnShowAll, gc);

		root.add(search, BorderLayout.NORTH);

		studentsTableModel = new DefaultTableModel(new Object[] { "ID", "Name", "CGPA", "Credits" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tblStudents = new JTable(studentsTableModel);
		tblStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && tblStudents.getSelectedRow() >= 0) {
					loadSelectedStudentIntoEditor();
				}
			}
		});
		JScrollPane sp = new JScrollPane(tblStudents);
		sp.setBorder(BorderFactory.createTitledBorder("Students"));
		root.add(sp, BorderLayout.CENTER);

		JPanel actions = new JPanel();
		JButton btnSortByName = new JButton("Sort by Name (QuickSort)");
		btnSortByName.addActionListener(e -> refreshStudentsTable(studentService.sortedByNameQuickSort()));
		JButton btnSortByCgpa = new JButton("Sort by CGPA (BubbleSort)");
		btnSortByCgpa.addActionListener(e -> refreshStudentsTable(studentService.sortedByCgpaBubbleSortDesc()));
		JButton btnDelete = new JButton("Delete Selected");
		btnDelete.addActionListener(e -> onDeleteSelected());
		actions.add(btnSortByName);
		actions.add(btnSortByCgpa);
		actions.add(btnDelete);
		root.add(actions, BorderLayout.SOUTH);

		return root;
	}

	private JPanel createSummaryPanel() {
		JPanel root = new JPanel(new GridBagLayout());
		root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.fill = GridBagConstraints.HORIZONTAL;

		lblStudentCount = new JLabel("Students: 0");
		lblStudentCount.setHorizontalAlignment(SwingConstants.LEFT);
		lblAverageCgpa = new JLabel("Average CGPA: 0.00");
		lblTopPerformer = new JLabel("Top Performer: N/A");

		int r = 0;
		gc.gridx = 0; gc.gridy = r++; root.add(lblStudentCount, gc);
		gc.gridx = 0; gc.gridy = r++; root.add(lblAverageCgpa, gc);
		gc.gridx = 0; gc.gridy = r++; root.add(lblTopPerformer, gc);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(e -> refreshSummary());
		gc.gridx = 0; gc.gridy = r++; root.add(btnRefresh, gc);

		return root;
	}

	private void onAddCourse(ActionEvent e) {
		try {
			String code = txtCourseCode.getText().trim();
			String title = txtCourseTitle.getText().trim();
			int units = Integer.parseInt(txtCreditUnits.getText().trim());
			double score = Double.parseDouble(txtScore.getText().trim());
			if (code.isEmpty() || title.isEmpty()) throw new IllegalArgumentException("Course code and title are required");
			Course c = new Course(code, title, units);
			Result r = new Result(c, score);
			courseTableModel.addRow(new Object[] { c.getCode(), c.getTitle(), c.getCreditUnits(), r.getScore(), r.getGradePoint() });
			clearCourseFields();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Credit units and score must be numeric", "Invalid Input", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onRemoveCourse() {
		int row = tblCourses.getSelectedRow();
		if (row >= 0) {
			courseTableModel.removeRow(row);
		} else {
			JOptionPane.showMessageDialog(this, "Select a course row to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onSaveStudent() {
		try {
			Student s = buildStudentFromForm();
			studentService.addStudent(s);
			JOptionPane.showMessageDialog(this, "Student saved successfully");
			clearAddForm();
			refreshStudentsTable(studentService.getAllStudents());
			refreshSummary();
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onUpdateStudent() {
		try {
			Student s = buildStudentFromForm();
			boolean ok = studentService.updateStudent(s);
			if (!ok) {
				JOptionPane.showMessageDialog(this, "Student ID not found", "Update Failed", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "Student updated successfully");
			clearAddForm();
			refreshStudentsTable(studentService.getAllStudents());
			refreshSummary();
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onDeleteSelected() {
		int row = tblStudents.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Select a student to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String id = studentsTableModel.getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this, "Delete student " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			boolean ok = studentService.deleteStudentById(id);
			if (ok) {
				refreshStudentsTable(studentService.getAllStudents());
				refreshSummary();
			}
		}
	}

	private void onSearchById() {
		String id = txtSearchId.getText().trim();
		if (id.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Enter an ID to search", "Input Needed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Student s = studentService.findByIdBinary(id);
		if (s == null) {
			JOptionPane.showMessageDialog(this, "No student found with ID: " + id, "Not Found", JOptionPane.INFORMATION_MESSAGE);
			studentsTableModel.setRowCount(0);
			return;
		}
		List<Student> one = new ArrayList<>();
		one.add(s);
		refreshStudentsTable(one);
	}

	private void onSearchByName() {
		String name = txtSearchName.getText().trim();
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Enter a name to search", "Input Needed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		refreshStudentsTable(studentService.searchByName(name));
	}

	private void refreshStudentsTable(List<Student> students) {
		studentsTableModel.setRowCount(0);
		for (Student s : students) {
			studentsTableModel.addRow(new Object[] { s.getId(), s.getName(), String.format("%.2f", s.calculateCgpa()), s.getTotalCredits() });
		}
	}

	private void refreshSummary() {
		lblStudentCount.setText("Students: " + studentService.getAllStudents().size());
		lblAverageCgpa.setText(String.format("Average CGPA: %.2f", studentService.computeAverageCgpa()));
		Student top = studentService.findTopPerformer();
		lblTopPerformer.setText(top == null ? "Top Performer: N/A" : String.format("Top Performer: %s (%.2f)", top.getName(), top.calculateCgpa()));
	}

	private void loadSelectedStudentIntoEditor() {
		int row = tblStudents.getSelectedRow();
		if (row < 0) return;
		String id = studentsTableModel.getValueAt(row, 0).toString();
		Student s = studentService.findByIdLinear(id);
		if (s == null) return;
		txtId.setText(s.getId());
		txtName.setText(s.getName());
		courseTableModel.setRowCount(0);
		s.getResults().forEach(r -> courseTableModel.addRow(new Object[] { r.getCourse().getCode(), r.getCourse().getTitle(), r.getCourse().getCreditUnits(), r.getScore(), r.getGradePoint() }));
	}

	private Student buildStudentFromForm() {
		String id = txtId.getText().trim();
		String name = txtName.getText().trim();
		if (id.isEmpty() || name.isEmpty()) throw new IllegalArgumentException("Student ID and name are required");
		Student s = new Student(id, name);
		int rows = courseTableModel.getRowCount();
		if (rows == 0) throw new IllegalArgumentException("Add at least one course");
		for (int i = 0; i < rows; i++) {
			String code = courseTableModel.getValueAt(i, 0).toString();
			String title = courseTableModel.getValueAt(i, 1).toString();
			int units = Integer.parseInt(courseTableModel.getValueAt(i, 2).toString());
			double score = Double.parseDouble(courseTableModel.getValueAt(i, 3).toString());
			Course c = new Course(code, title, units);
			Result r = new Result(c, score);
			s.addResult(r);
		}
		return s;
	}

	private void clearAddForm() {
		txtId.setText("");
		txtName.setText("");
		clearCourseFields();
		courseTableModel.setRowCount(0);
	}

	private void clearCourseFields() {
		txtCourseCode.setText("");
		txtCourseTitle.setText("");
		txtCreditUnits.setText("");
		txtScore.setText("");
	}
}