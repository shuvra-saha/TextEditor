package textEditor;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.text.JTextComponent;

public class Replace{

	private JFrame findFrame = new JFrame("প্রতিস্থাপন");
	private JTextField findThis = new JTextField();
	private JLabel fLabel = new JLabel("লিখুন    ");
	private JButton findBtn = new JButton("সন্ধান");
	private JButton findNxtBtn = new JButton("সন্ধান পরবর্তী");
	private JLabel replaceLabel = new JLabel("প্রতিস্থাপন  ");
	private JTextField replaceWithThis = new JTextField();
	private JButton rplcBtn = new JButton("প্রতিস্থাপন");
	private JButton rplcAllBtn = new JButton("প্রতিস্থাপন সব");
	private JButton exitBtn = new JButton("বাহির");
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private static int i = 1;

	public Replace(JTextComponent pan) {
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		Font font = new Font("Siyam Rupali", Font.PLAIN, 14);

		// >>>layout manage
		findFrame.setLayout(new GridLayout(4, 1));

		BoxLayout layout1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
		BoxLayout layout2 = new BoxLayout(panel2, BoxLayout.X_AXIS);
		BoxLayout layout3 = new BoxLayout(panel3, BoxLayout.X_AXIS);
		BoxLayout layout4 = new BoxLayout(panel4, BoxLayout.X_AXIS);

		panel1.setLayout(layout1);
		panel2.setLayout(layout2);
		panel3.setLayout(layout3);
		panel4.setLayout(layout4);

		panel1.setBorder(BorderFactory.createEtchedBorder());
		panel2.setBorder(BorderFactory.createEtchedBorder());
		panel3.setBorder(BorderFactory.createEtchedBorder());
		panel4.setBorder(BorderFactory.createEtchedBorder());

		// >>>>>>>>>>setFont.....
		findFrame.setFont(font);
		findThis.setFont(font);
		fLabel.setFont(font);
		findBtn.setFont(font);
		findNxtBtn.setFont(font);
		exitBtn.setFont(font);
		replaceLabel.setFont(font);
		replaceWithThis.setFont(font);
		rplcBtn.setFont(font);
		rplcAllBtn.setFont(font);

		// add panel 1
		panel1.add(fLabel);
		panel1.add(findThis);

		// add panel 2
		panel2.add(findBtn);
		panel2.add(findNxtBtn);

		// add panel 3
		panel3.add(replaceLabel);
		panel3.add(replaceWithThis);

		// add panel 4
		panel4.add(rplcBtn);
		panel4.add(rplcAllBtn);
		panel4.add(exitBtn);

		findFrame.add(panel1);
		findFrame.add(panel2);

		findFrame.add(panel3);
		findFrame.add(panel4);

		findFrame.setSize(400, 200);
		findFrame.setResizable(false);
	
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		findFrame.setLocation(center.x - 300 / 2, center.y - 100 / 2);
		findFrame.setVisible(true);
		findFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// >>>>>>>>>>>>>>>>>>>>>>>>>>action listener for findbtn
		findBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pattern = findThis.getText();
				find(pan, pattern);

			}
		});

		findNxtBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException {
				String pattern = findThis.getText();
				int size = list.size();
				if(size>0){
				int start = list.get(i);
				int end = start + pattern.length();
				pan.select(start, end);
				i++;
				if (i == size) {
					i = 0;
				}
				}
			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>action for exit
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				findFrame.setVisible(false);

			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>action for replace
		rplcBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String rpl = replaceWithThis.getText();
				replaceThis(pan, rpl);
				
			}
		});

		rplcAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rpl = replaceWithThis.getText();
				String pattern = findThis.getText();
				replaceAll(pan, pattern, rpl);
			}
		});
	}

	public void find(JTextComponent text, String pattern) {
		list = new StringMatching().KMPSearch(pattern, text.getText());
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Could not find " + findThis.getText());
			return;
		}
		Integer s = list.get(0); // unboxing
		int start = s;
		int end = start + pattern.length();
		text.select(start, end);
		return;
	}
	private void replaceAll(JTextComponent text, String pattern, String rpl) {
		Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
		Matcher m = p.matcher(text.getText());
		if (m.find()){
			text.setText(m.replaceAll(rpl));
		}
	}

	public void replaceThis(JTextComponent text, String rpl) {
		text.replaceSelection(rpl);
	}
}
