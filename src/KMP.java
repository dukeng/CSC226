

/* 
Step one: run KMP to construct the matching array
Step two: match the txt with the constructed pattern
Running time: O(m+n)
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP{
	private static String pattern;
	private static int[] matchArray;
	private static int patternLength;

	// Construct array here
    public KMP(String pattern){
		this.pattern = pattern;
	    patternLength = pattern.length();
	    this.matchArray = new int[patternLength];
	    int j = 0;
	    int i = 1;
	    matchArray[0] = 0;
	    while(i < patternLength){
		    if(pattern.charAt(i) == pattern.charAt(j)){ // if match
			    j++;
			    matchArray[i] = j;
			    i++;
		    }else{
			    if(j != 0){
				    j = matchArray[j - 1];
			    }else{
					matchArray[i] = j;
				    i++;
			    }
		    }
	    }
    }
    public static int search(String txt){
		int textLength = txt.length();
	    int i = 0; // txt index
	    int j = 0; // pattern index
	    while (i < textLength){
		    if (pattern.charAt(j) == txt.charAt(i)){ // if it's a match, then increment both i and j
			    i++;
			    j++;
		    }
		    if(j == patternLength){ // if we reach the end of the matching string
				return i - patternLength; // return the first index that matches
		    }else if (i < textLength && pattern.charAt(j) != txt.charAt(i)){ // if it is not a match
				if (j != 0){ // if it is not the beginning of the text
					j = matchArray[j - 1]; // set j back to the previous matchable point
				}else{
					i = i+1; // increment the txt since pattern reaches 0
				}
		    }
	    }
	    return textLength;
    }

        
  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}
			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}
		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
		
		
	}

}




