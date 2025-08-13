package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import app.ui.StudentManagementApp;

public class Main {
	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// Fall back to default look and feel
		}

		SwingUtilities.invokeLater(() -> {
			StudentManagementApp app = new StudentManagementApp();
			app.setVisible(true);
		});
	}
}