package textEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontClass extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String fName = "Siyam Rupali";
	private static int fStyle = 4;
	private static int fSize = 16;

	private final String[] fontNameData = { "Kalpurush", "Siyam Rupali", "Shonar Bangla", "SutonnyOMJ" };
	private final String[] fontStyleData = { "বোল্ড", "ইটালিক", "ইটালিক বোল্ড", "প্লেইন" };
	private Integer[] fontSizeData = new Integer[100];
	private JList<String> fontName, fontStyle;

	private JList<Integer> fontSize;
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();

	private JPanel panel1a = new JPanel();
	private JPanel panel1b = new JPanel();
	private JPanel panel1c = new JPanel();
	private JLabel fontNameLabel, fontStyleLabel, fontSizeLabel;

	private static Font font = new Font("Siyam Rupali", 4, 16);
	private Font fontUse = new Font("Siyam Rupali", 4, 16);

	public FontClass() {
		setFont(fontUse);
		setTitle("ফন্ট");
		setLayout(new GridLayout(2, 1));
		// >>>>>>>>>>>>>panel 1

		fontNameLabel = new JLabel("ফন্টের নাম");
		fontStyleLabel = new JLabel("ফন্ট স্টাইল");
		fontSizeLabel = new JLabel("ফন্টের আকার");

		panel1a.setLayout(new BorderLayout());
		panel1b.setLayout(new BorderLayout());
		panel1c.setLayout(new BorderLayout());

		panel1.setLayout(new GridLayout(1, 3, 5, 5));

		Integer incr = 2;
		Integer size = 8;
		Integer max = 72;

		for (int i = 0; size <= max; i++) {
			fontSizeData[i] = size;
			size += incr;
		}

		fontName = new JList<>(fontNameData);
		fontStyle = new JList<>(fontStyleData);
		fontSize = new JList<>(fontSizeData);

		fontStyle.setFont(fontUse);
		fontNameLabel.setFont(fontUse);
		fontStyleLabel.setFont(fontUse);
		fontSizeLabel.setFont(fontUse);

		fontName.setLayoutOrientation(JList.VERTICAL);
		fontStyle.setLayoutOrientation(JList.VERTICAL);
		fontSize.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller1 = new JScrollPane(fontName);
		panel1a.add(fontNameLabel, BorderLayout.NORTH);
		panel1a.add(listScroller1, BorderLayout.CENTER);
		JScrollPane listScroller2 = new JScrollPane(fontStyle);
		panel1b.add(fontStyleLabel, BorderLayout.NORTH);
		panel1b.add(listScroller2, BorderLayout.CENTER);
		JScrollPane listScroller3 = new JScrollPane(fontSize);
		panel1c.add(fontSizeLabel, BorderLayout.NORTH);
		panel1c.add(listScroller3, BorderLayout.CENTER);

		panel1.add(panel1a);
		panel1.add(panel1b);
		panel1.add(panel1c);

		add(panel1);

		fontName.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {

			}
		});

		// >>>>>>>>>panel 2
		panel2.setLayout(new BorderLayout());

		JLabel sample = new JLabel("নমুনা", SwingConstants.CENTER);
		sample.setFont(fontUse);
		sample.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		JTextArea ar = new JTextArea("নন্দনকানন");
		ar.setAlignmentX(SwingConstants.CENTER);
		ar.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		ar.setEditable(false);
		ar.setFont(font);

		JButton setbtn = new JButton("সেট");
		setbtn.setFont(fontUse);
		setbtn.setMaximumSize(new Dimension(5, 10));
		setbtn.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		panel2.add(sample, BorderLayout.NORTH);
		panel2.add(ar, BorderLayout.CENTER);
		panel2.add(setbtn, BorderLayout.SOUTH);
		add(panel2);

		setbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					fName = fontName.getSelectedValue();
					fStyle = fontStyle.getSelectedIndex() + 1;

					Integer fs = fontSize.getSelectedValue();
					fSize = fs;
					font = new Font(fName, fStyle, fSize);
					ar.setFont(font);
					Design.setTextFont(font);
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setResizable(false);
		setSize(350, 500);
		setVisible(true);
		
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(center.x - 350 / 2, center.y - 500 / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}