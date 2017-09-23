package textEditor;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;

import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ColorSyntax {

	public static final Color KEYWORD_COLOR1 = Color.BLUE;
	public static final Color KEYWORD_COLOR2 = Color.RED;
	public static final Color KEYWORD_COLOR3 = Color.GREEN;
	public static final Color KEYWORD_COLOR4 = Color.magenta;

	private StyledDocument textEditorDoc;

	public static final String[] KEYWORDS1 = new String[] { "ধরি", "যদি", "নাহলে যদি", "নাহলে", "এবং", "অথবা", "সত্য",
			"মিথ্যা", "হা", "না", "লুপ", "চলবে", "থামো", "নাল", "ফাংশন", "রিটার্ন", "অসীম", "_ইন্ডেক্স" };;

	public static final String[] KEYWORDS2 = new String[] { "এর", "বার", "হয়", "পায়", "থাকে", "হতে", "থেকে", "চেয়ে",
			"মান", "সমান", "ছোট", "বড়", "কম", "বেশি", "দেখতে", "শুনতে", "বলতে ", "বুঝতে" };

	public static final String[] KEYWORDS3 = new String[] { "দেখাও", "ইনপুট", "_টাইপ", "_বর্গমূল", "_নাম্বার",
			"_পূর্ণসংখ্যা", "_সময়", "_পাই" };

	
	public static String quotation_Regex = "\"([^\"]*)\"|\'([^\']*)\'";
	private Pattern pattern1, pattern2, pattern3, pattForQt;
	private JEditorPane textEditor;
	public static String KEYWORDS2_REGEX;
	public static String KEYWORDS3_REGEX;
	public static String KEYWORDS1_REGEX;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private static String makeRegex(String[] KEYWORDS) {
		StringBuilder buff = new StringBuilder("");
		buff.append("(");
		for (String keyword : KEYWORDS) {
			buff.append("\\b").append(keyword).append("\\b").append("|");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(")");
		return buff.toString();

	}

	public ColorSyntax(JEditorPane pan) {
		KEYWORDS1_REGEX = makeRegex(KEYWORDS1);
		KEYWORDS2_REGEX = makeRegex(KEYWORDS2);
		KEYWORDS3_REGEX = makeRegex(KEYWORDS3);
		this.textEditor = pan;
		textEditorDoc = (StyledDocument) pan.getDocument();
		pan.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

		pattern1 = Pattern.compile(KEYWORDS1_REGEX, Pattern.UNICODE_CHARACTER_CLASS);
		pattern2 = Pattern.compile(KEYWORDS2_REGEX, Pattern.UNICODE_CHARACTER_CLASS);
		pattern3 = Pattern.compile(KEYWORDS3_REGEX, Pattern.UNICODE_CHARACTER_CLASS);
		pattForQt = Pattern.compile(quotation_Regex, Pattern.UNICODE_CHARACTER_CLASS);

		colorSyntax();

		pan.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				colorSyntax();
			}
		});
	}

	private void colorSyntax() {
		clearTextColors(textEditor);
		Matcher match = pattern1.matcher(textEditor.getText());
		if (match != null) {
			while (match.find()) {
				updateTextColor(match.start(), match.end() - match.start(), KEYWORD_COLOR1);
			}
		}
		Matcher match2 = pattern2.matcher(textEditor.getText());
		if (match2 != null) {
			while (match2.find()) {
				updateTextColor(match2.start(), match2.end() - match2.start(), KEYWORD_COLOR2);
			}
		}

		Matcher match3 = pattern3.matcher(textEditor.getText());
		if (match3 != null) {
			while (match3.find()) {
				updateTextColor(match3.start(), match3.end() - match3.start(), KEYWORD_COLOR3);
			}
		}

		Matcher m = pattForQt.matcher(textEditor.getText());
		if (m != null) {
			while (m.find()) {
				updateTextColor(m.start(), m.end() - m.start(), KEYWORD_COLOR4);
			}
		}

	}

	private void updateTextColor(int offset, int length, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		textEditorDoc.setCharacterAttributes(offset, length, aset, true);
	}

	private void clearTextColors(JEditorPane textEditor2) {
		updateTextColor(0, textEditor2.getText().length(), Color.BLACK);
	}

}
