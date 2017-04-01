//*************************************************************************
// Reflector.java       Author: Huff                     5/6/2016
//
// Models the functionality of the Enigma reflector
//*************************************************************************
public class Reflector {
	private String code;
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	//********************************************************************
	// Constructor: creates a reflector and sets the corresponding code
	//********************************************************************
	public Reflector(char c){
		if(c == 'B'){
			code = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
		}
		if (c == 'C'){
			code = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
		}
	}

	//********************************************************************
	// Takes an input character and returns the corresponding coded character
	//********************************************************************
	public char reflect(char c){
		int cPos = getCPos(c);

		return code.charAt(cPos);
	}

	//********************************************************************
	// Returns a character's position in the alphabet
	//********************************************************************
	private int getCPos(char c){
		boolean found = false;
		int cPos = -1;
		for(int i = 0; i <= 25 && !found; i++){
			if(alphabet.charAt(i) == c){
				cPos = i;
				found = true;
			}
		}

		return cPos;
	}

}
