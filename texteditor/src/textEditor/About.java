package textEditor;

import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class About extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea area = new JTextArea();

	public About() {
		JPanel img = new JPanel();

		img.add(new JLabel(new ImageIcon("about.png")));
		add(img);
		area.setEditable(false);

		setSize(400, 300);

		setResizable(false);

		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(center.x - 400 / 2, center.y - 300 / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}
}
