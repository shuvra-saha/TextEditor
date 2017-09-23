package textEditor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.Keymap;

public class Design {

	private String oldText = "";
	public static JFrame frame = new JFrame("বাংলা টেক্সট ইডিটর");
	private static final JTextPane pan = new JTextPane();
	private static JTextArea lineArea = new JTextArea(2, 3);
	private static JPanel panel = new JPanel();
	private static JMenuBar mnbr = new JMenuBar();
	private static JMenuItem newItem, openItem, saveItem, searchItem, replaceItem, cutItem, copyItem, pasteItem,
			aboutItem, fontSize, showYes, showNo;
	private static JMenu showLine = new JMenu("লাইন নম্বর");;
	private static JMenu editMenu = new JMenu("সম্পাদন করুন   ।");
	private static JMenu about = new JMenu("সম্বন্ধ");
	private static File file = null;
	private static JFileChooser fileChoose = new JFileChooser();
	private static JMenu fontMenu = new JMenu("ফন্ট    ।");
	private static JMenuItem ext = new JMenuItem("বাহির হউন");
	private static JMenu fileMenu = new JMenu("ফাইল   ।");
	private static Font fontForText = new Font("Siyam Rupali", Font.PLAIN, 16);
	private Font font = new Font("Siyam Rupali", 4, 16);

	public Design() {
		view();
		new LineNumber(pan, lineArea);
	}

	public static void setTextFont(Font font) {
		fontForText = font;
		pan.setFont(fontForText);
		lineArea.setFont(fontForText);
	}

	public void view() {
		mnbr.setBackground(Color.lightGray);
		frame.getContentPane().setLayout(new BorderLayout());
		Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMaximumSize(DimMax);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(300, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("du.png"));

		panel.setLayout(new BorderLayout());
		panel.add(pan, BorderLayout.CENTER);
		panel.add(lineArea, BorderLayout.WEST);

		lineArea.setEditable(false);

		JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(16);//for speed 

		newItem = new JMenuItem("নতুন");
		openItem = new JMenuItem("খুলুন");
		saveItem = new JMenuItem("সংরক্ষণ");
		searchItem = new JMenuItem("সন্ধান ");
		replaceItem = new JMenuItem("প্রতিস্থাপন");
		cutItem = new JMenuItem();
		copyItem = new JMenuItem();
		pasteItem = new JMenuItem();
		aboutItem = new JMenuItem("ইডিটর সম্বন্ধে জানুন");
		fontSize = new JMenuItem("আকার-ধরন");
		showYes = new JMenuItem("হা");
		showNo = new JMenuItem("না");

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set font for the contents
		fileMenu.setFont(font);
		editMenu.setFont(font);
		about.setFont(font);
		ext.setFont(font);
		pan.setFont(fontForText);
		lineArea.setFont(fontForText);
		newItem.setFont(font);
		openItem.setFont(font);
		saveItem.setFont(font);
		searchItem.setFont(font);
		replaceItem.setFont(font);
		cutItem.setFont(font);
		copyItem.setFont(font);
		pasteItem.setFont(font);
		aboutItem.setFont(font);
		fontMenu.setFont(font);
		fontSize.setFont(font);
		showLine.setFont(font);
		showYes.setFont(font);
		showNo.setFont(font);

		
		// >>>>>>>>>>>>>>>>>>>>>>>>>add fileMenu item
		fileMenu.add(newItem);
		fileMenu.addSeparator();
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(ext);

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>> add to about menu
		about.add(aboutItem);

		// >>>>>>>>>>>>>>>>>>>>>>>>>>add editMenu
		editMenu.add(searchItem);
		editMenu.addSeparator();
		editMenu.add(replaceItem);
		editMenu.addSeparator();
		editMenu.add(cutItem);
		editMenu.addSeparator();
		editMenu.add(copyItem);
		editMenu.addSeparator();
		editMenu.add(pasteItem);
		editMenu.addSeparator();
		editMenu.add(showLine);

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>> add to font menu
		fontMenu.add(fontSize);
		
		//line area
		lineArea.setVisible(false);
		showLine.add(showYes);
		showLine.add(showNo);
		
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>add to main menu bar
		mnbr.add(fileMenu);
		mnbr.add(editMenu);
		mnbr.add(fontMenu);
		mnbr.add(about);
		mnbr.setPreferredSize(new Dimension(50, 60)); // >>>>> give size

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>add to main frame

		frame.add(mnbr, BorderLayout.BEFORE_FIRST_LINE);
		frame.add(scroll, BorderLayout.CENTER);
		frame.setVisible(true);


		//line number show or not
		showNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lineArea.setVisible(false);

			}
		});

		showYes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lineNumber();
				lineArea.setVisible(true);

			}

		});

		
		// >>>>>>>>>>>>>>cut ,copy ,paste
		
		cutItem.setText("কাটুন");
		cutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				copy();
				pan.replaceSelection("");
			}
		});

		// copyItem.setAction(new DefaultEditorKit.CopyAction());
		copyItem.setText("কপি");
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				copy();

			}
		});

		// >>>>>>>>>>>>>>paste
		// pasteItem.setAction(new DefaultEditorKit.PasteAction());
		pasteItem.setText("পেস্ট");
		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				paste();
				lineNumber();
				new ColorSyntax(pan);
			}
		});
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>action listener for menu save item

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frame.getTitle().equals("বাংলা টেক্সট ইডিটর")) {
					saveFile();
				} else if (frame.getTitle().equals("Untitled")) {
					saveFile();
				} else {
					if (frame.getTitle().equals("Untitled") == false) {
						String newText = pan.getText();
						if (oldText.equals(newText) == false) {
							saveAs();
						}
					}
				}
			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>action listener for menu new item

		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setTitle("Untitled");
				lineArea.setText("১\n");
				pan.setText("");
				// new EditorMain();

			}

		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>action listener for menu open item

		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lineArea.setText("১\n");
				pan.setText("");
				openFile();
				oldText = pan.getText();
			}
		});

		// >>>>>>>>>>>>>>>>>>>>exit ......
		ext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>search item action
		searchItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Find(pan);

			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>search and replace action
		replaceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					new Replace(pan);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		// >>>>>>>>>>>>>>>>>>>>>>>>aboutItem action
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new About();
			}
		});

		// >>>>>>>>>>>>>>>>>>>>fontSize ...................
		fontSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FontClass();
			}
		});
		Keymap keymap = pan.getKeymap();
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK);
		keymap.removeKeyStrokeBinding(key);

		keyPressed();

	}

	private void saveFile() {
		// JFileChooser
		fileChoose = new JFileChooser();
		int option = fileChoose.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				file = fileChoose.getSelectedFile();
				frame.setTitle(file.getName());
				FileOutputStream fout = new FileOutputStream(file.getPath());

				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fout, "UTF-8"));

				out.write(pan.getText());
				out.close();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}
		}
	}

	private void saveAs() {
		try {
			// file = fileChoose.getSelectedFile();
			frame.setTitle(file.getName());
			FileOutputStream fout = new FileOutputStream(file.getPath());

			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fout, "UTF-8"));

			out.write(pan.getText());
			out.close();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
	}

	private void openFile() {
		// JFileChooser
		fileChoose = new JFileChooser();
		// fileChoose.setFont(font);
		int option = fileChoose.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				file = fileChoose.getSelectedFile();
				frame.setTitle(file.getName());
				FileInputStream myFile = new FileInputStream(file.getPath());
				BufferedReader br = new BufferedReader(new InputStreamReader(myFile, "UTF-8"));
				try {
					pan.read(br, null);
					br.close();
					new ColorSyntax(pan);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			lineNumber();

		}
	}

	private void lineNumber() {
		LineNumber line = new LineNumber();
		long lineNum = line.getLine(pan);
		lineArea.setText("১\n");
		LineNumber.setLn(lineNum);
		for (int i = 2; i <= lineNum; i++) {
			String eng = Integer.toString(i);
			String num = line.EngToBan(eng);
			lineArea.append(num + "\n");
			LineNumber.setLn(lineNum);
		}
	}

	public void copy() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		TransferHandler transferHandler = pan.getTransferHandler();
		transferHandler.exportToClipboard(pan, clipboard, TransferHandler.COPY);
	}

	public void paste() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		TransferHandler transferHandler = pan.getTransferHandler();
		transferHandler.importData(pan, clipboard.getContents(null));

	}

	// >>>>>>>>>>>>>>>>>>>>>> keylistener
	public void keyPressed() {
		try {
			newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
			saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
			openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
			ext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
			searchItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
			replaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
			cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
			copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
			pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
			aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}