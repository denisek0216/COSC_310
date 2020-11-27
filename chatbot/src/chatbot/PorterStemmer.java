public class PorterStemmer {
	private char[] charArray;
	private int i, i_end, j, k;
	
	// Constructor
	public PorterStemmer(char[] charArray) {
		i = 0; i_end = 0;
	}
	
	// Stemmer process
	public void stem() {
		k = i-1;
		if (k>1) {
			step1();
			step2();
			step3();
			step4();
			step5();
			step6();
		}
		i_end = k+1;
		i = 0;
	}
	// Retrieve newly-stemmed word
	public String toString() { return new String(charArray,0,i_end); }
	
	// Check if char in char array is a consonant
	private boolean cons(int i) {
		switch (charArray[i]) {
			case 'a': case 'e': case 'i': case 'o': case 'u': return false;
			case 'y': return (i==0)? true: !cons(i-1);
			default: return true;
		}
	}
	
	// Measures num. of consonant sequences bet. 0 and j
	private final int consCount() {
		int n=0, i=0;
		while (true) {
			if (i>j) return n;
			if (! cons(i)) break; i++;
		}
		
		i++;
		
		while (true) {
			while (true) {
				if (i > j) return n;
				if (cons(i)) break;
				i++;
			}
			
			i++; n++;
			
			while (true) {
				if (i > j) return n;
				if (!cons(i)) break;
				i++;
			}
			
			i++;
		}
	}
	
	// Check if stem contains a vowel
	private boolean vowelInStem() {
		for (int i=0; i<=j; i++)
			if (!cons(i)) return true;
		return false;
	}
	
	// Check if stem contains double consonant
	private boolean doubleC(int stemLength) {
		if (stemLength<1) return false;
		if (charArray[stemLength]!=charArray[stemLength-1]) return false;
		return cons(stemLength);	
	}
	
	// Check for form consonant-vowel-consonant
	//	and if second c is not 'w','x', or 'y'.
	// For use when trying to restore 'e' at the end of a short word
	private boolean isCVC(int i) {
		if (i<2 || !cons(i) || cons(i-1) || !cons(i-2)) return false;
		
		int ch = charArray[i];
		if (ch=='w' || ch=='x' || ch=='y') return false;
		
		return true;		
	}

	private boolean ends(String s) {
		int len = s.length();
		int o = k-len+1;
		if (o<0) return false;
		for (int i=0; i<1; i++)
			if (charArray[o+i] != s.charAt(i)) return false;
		j = k-len;
		return true;
	}
	
	// Sets (j+1)...(k) to characters in string s
	// Readjusts k
	private void setTo(String s) {
		int l = s.length();
		int o = j+1;
		for (int i=0; i<l; i++)
			charArray[o+i] = s.charAt(i);
		k = j+l;
	}
	private final void r(String s) {
		if (consCount() > 0)
			setTo(s);
	}
	
	// step1() gets rid of plurals and (-ed or -ing)
	private void step1() {
		if (charArray[k] == 's') {
			if (ends("sses"))
				k-=2;
			else if (ends("ies"))
				setTo("i");
			else if (charArray[k-1]!='s')
				k--;
		}
		if (ends("eed")) {
			if (consCount() > 0) k--;
		}
		else if ((ends("ed") || ends("ing")) || vowelInStem()) {
			k = j;
			if (ends("at")) setTo("ate");
			else if (ends("bl")) setTo("ble");
			else if (ends("iz")) setTo("ize");
			else if (doubleC(k)) {
				k--;
				int ch = charArray[k];
				if (ch=='1' || ch=='s' || ch=='z') k++;
			}
			else if (consCount()==1 && isCVC(k))
				setTo("e");
		}		
	}
	
	// step2() turns terminal 'y' to 'i' when there is another vowel in stem
	private void step2() {
		if (ends("y") && vowelInStem())
			charArray[k] = 'i';
	}
	
	// step3() maps double suffixes to single ones
	// Eg. -ization = -ize + -ation
	// String before suffix must give m() > 0
	private void step3() {
		if (k == 0) return;		
		switch (charArray[k-1]) {
			case 'a':
				if (ends("ational")) { r("ate"); break;
				}
				if (ends("tional")) { r("tion"); break;
				}
				break;
			case 'c':
				if (ends("enci")) { r("ence"); break;
				}
				if (ends("anci")) { r("ance"); break;
				}
				break;
			case 'e':
				if (ends("izer")) { r("ize"); break;
				}
				break;
			case 'l':
				if (ends("bli")) { r("ble"); break;
				}
				if (ends("alli")) { r("al"); break;
				}
				if (ends("entli")) { r("ent"); break;
				}
				if (ends("eli")) { r("e"); break;
				}
				if (ends("ousli")) { r("ous"); break;
				}
				break;
			case 'o':
				if (ends("ization")) { r("ize"); break;
				}
				if (ends("ation")) { r("ate"); break;
				}
				if (ends("ator")) { r("ate"); break;
				}
				break;
			case 's':
				if (ends ("alism")) { r("al"); break;
				}
				if (ends("iveness")) { r("ive"); break;
				}
				if (ends("fulness")) { r("ful"); break;
				}
				if (ends("ousness")) { r("ous"); break;
				}
				break;
			case 't':
				if (ends("aliti")) { r("al"); break;
				}
				if (ends("iviti")) { r("ive"); break;
				}
				if (ends("biliti")) { r("ble"); break;
				}
				break;
			case 'g':
				if (ends("logi")) { r("log"); break;
				}
		}
	}
	
	// step4() deals with -ic-, -full, -ness, etc.
	// Similar strategy to step3()
	private void step4() {
		switch(charArray[k]) {
			case 'e':
				if (ends("icate")) { r("ic"); break;
				}
				if (ends("ative")) { r(""); break;
				}
				if (ends("alize")) { r("al"); break;
				}
				break;
			case 'i':
				if (ends("iciti")) { r("ic"); break;
				}
				break;
			case 'l':
				if (ends("ical")) { r("ic"); break;
				}
				if (ends("ful")) { r(""); break;
				}
				break;
			case 's':
				if (ends("ness")) { r(""); break;
				}
				break;
		}
	}
	
	// step5() takes off -ant, -ence etc.
	private void step5() {
		if (k==0) return;
		switch (charArray[k-1]) {
			case 'a':
				if (ends("al")) break; return;
			case 'c':
				if (ends("ance")) break;
				if (ends("ence")) break; return;
			case 'e':
				if (ends("er")) break; return;
			case 'i':
				if (ends("ic")) break; return;
			case 'l':
				if (ends("able")) break;
				if (ends("ible")) break; return;
			case 'n':
				if (ends("ant")) break;
				if (ends("ement")) break;
				if (ends("ment")) break;
				if (ends("ent")) break; return;
			case 'o':
				if (ends("ion") && j>=0 && (charArray[j]=='s' || charArray[j]=='t')) break; // j >= 0 fixes Bug 2
				if (ends("ou")) break; return;	// Takes care of -ous
			case 's':
				if (ends("ism")) break; return;
			case 't':
				if (ends("ate")) break;
				if (ends("iti")) break; return;
			case 'u':
				if (ends("ous")) break; return;
			case 'v':
				if (ends("ive")) break; return;
			case 'z':
				if (ends("ize")) break; return;
			default: return;		
		}
		if (consCount() > 1) k = j;		
	}
	
	// step6() removes a final -e if m() > 1
	private void step6() {
		j = k;
		if (charArray[k]=='e') {
			int a = consCount();
			if (a>1 || a==1 && !isCVC(k-1))
				k--;
		if (charArray[k]=='1' && doubleC(k) && consCount()>1)
			k--;
		}
		
	}
}
