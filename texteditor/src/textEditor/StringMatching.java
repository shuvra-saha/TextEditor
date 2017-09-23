package textEditor;

import java.util.ArrayList;

public class StringMatching {
	private int patLen, textLen;
	private ArrayList<Integer> list = new ArrayList<Integer>();

	public ArrayList<Integer> KMPSearch(String pat, String txt) {
		patLen = pat.length();
		textLen = txt.length();
		int lps[] = new int[patLen];
		int j = 0;
		computePreArray(pat, patLen, lps);
		int i = 0;
		while (i < textLen) {
			if (pat.charAt(j) == txt.charAt(i)) {
				j++;
				i++;
			}
			if (j == patLen) {
				Integer in = (i - j); // autoboxing
				list.add(in);
				j = lps[j - 1];
			} else if (i < textLen && pat.charAt(j) != txt.charAt(i)) {
				if (j != 0)
					j = lps[j - 1];
				else
					i = i + 1;
			}
		}
		return list;
	}

	private void computePreArray(String pat, int patLen, int lps[]) {

		int len = 0;
		int i = 1;
		lps[0] = 0;

		while (i < patLen) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else {
				if (len != 0) {
					len = lps[len - 1];
				} else 
				{
					lps[i] = len;
					i++;
				}
			}
		}
	}

}