/**
 * This class only implements the bad heuristic of BayerMoore
 * Running time: best case O(n/m). worst case: O(nm)
 */
public class BayerMoore {
	private static int NO_OF_CHARS = 256;

	private void badCharHeuristic(String pattern, int[] badchar){
		// Init all occurrences to be -1
		for(int k = 0; k < NO_OF_CHARS; k++){
			badchar[k] = -1;
		}
		// Fill the actual value of last occurrence of a character
		for(int k = 0; k < pattern.length(); k++){
			badchar[pattern.charAt(k)] = k;
		}
	}

	private int max(int a, int b){
		return (a > b) ? a : b;
	}
	public BayerMoore(String pattern, String txt){
		int badchar[] = new int[NO_OF_CHARS];
		badCharHeuristic(pattern, badchar);
		int shift = 0; // shift of the pattern with respect to the text
		int p_length = pattern.length();
		int t_length = txt.length();
		while(shift <= (t_length - p_length)){
			int j = p_length -1; // last index of the pattern
			// Match the pattern from right to left at offset shift: keep reducing index j of pattern while matching char of pattern and txt at this shift
			while (j >= 0 && pattern.charAt(j) == txt.charAt(shift + j)){
				j--;
			}
			if(j < 0){// matched
				System.out.println("Pattern occur at shift " + shift);
				//Shift the pattern so that the next char in the text aligns with the last occurrence of it in pattern.
				shift = (shift + p_length < t_length) ?
						(p_length - badchar[txt.charAt(shift + p_length)]) :
						1;
				return; // TODO: return because I didnt finish debugging
			}else{ // mismatch
				/*
				Shift the pattern so that the bad char in text aligns with
				the last occurrence of it in pattern. The max function is used to
				make sure that we get a positive shift. We may get a negative shift
				if the last occurrence of bad character in pattern is on the right side
				of the current char
				 */
				shift += max(1, j - badchar[txt.charAt(shift + j)]);
			}
			System.out.println(shift);
		}

	}


	public static void main(String[] args){
		String txt = "ABAAABCD";
		String pat = "ABC";
		BayerMoore search = new BayerMoore(pat, txt);

	}
}
