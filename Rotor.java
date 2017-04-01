//*************************************************************************
// Rotor.java       Author: Huff                     5/6/2016
//
// Models the functionality of the Enigma rotors
//*************************************************************************
public class Rotor {
	private int rotorNum;
	private int position;
	private int ringSetting;
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String code;
	private String invertedCode;

	//********************************************************************
	// Constructor, creates a rotor with one of 5 preset codes
	//********************************************************************
	public Rotor(int r) {
		rotorNum = r;
		position = 0;
		ringSetting = 25;
		switch (r) {
		case 1: code = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
		invertedCode = "UWYGADFPVZBECKMTHXSLRINQOJ";
		break;
		case 2: code = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
		invertedCode = "AJPCZWRLFBDKOTYUQGENHXMIVS";
		break;
		case 3: code = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
		invertedCode = "TAGBPCSDQEUFVNZHYIXJWLRKOM";
		break;
		case 4: code = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
		invertedCode = "HZWVARTNLGUPXQCEJMBSKDYOIF";
		break;
		case 5: code = "VZBRGITYUPSDNHLXAWMJQOFECK";
		invertedCode = "QCYLXWENFTZOSMVJUDKGIARPHB";
		break;	
		}	
	}

	//********************************************************************
	// Rotor number accessor
	//********************************************************************
	public int getRotorNum(){
		return rotorNum;
	}

	//********************************************************************
	// Position accessor
	//********************************************************************
	public int getPosition(){
		return position;
	}

	//********************************************************************
	// Ring settings accessor
	//********************************************************************
	public int getRingSetting(){
		return ringSetting;
	}

	//********************************************************************
	// Code accessor
	//********************************************************************
	public String getRotorCode(){
		return code;
	}

	//********************************************************************
	// Position mutator
	//********************************************************************
	public void setRotorPosition(int p){
		position = p;
	}

	//********************************************************************
	// Determines if an input ring setting is valid and if so, sets the
	// ring setting to the input
	//********************************************************************
	public void setRingSetting(int setting){
		if(setting <= 25 && setting >= 0)
			ringSetting = setting;
	}

	//********************************************************************
	// Steps the rotor one position forward. If the rotor passes the ring 
	// setting, true is returned
	//********************************************************************
	public boolean rotate(){
		if(position == ringSetting){
			if(ringSetting == 25){
				position = 0;
				return true;
			} 
			else{
				position++;
				return true;
			}
		}

		if(position == 25){
			position = 0;
			return false;
		}
		else{
			position++;
			return false;
		}
	}

	//********************************************************************
	// Steps the rotor one position backward. If the rotor passes the ring 
	// setting, true is returne
	//********************************************************************
	public boolean previousSettings(){
		if(position == 0){
			position = 25;
			if(position == ringSetting){
				return true;
			} 
			else {
				return false;
			}
		} 
		else {
			position--;
			if(position == ringSetting){
				return true;
			} 
			else {
				return false;
			}
		}
	}

	//********************************************************************
	// Takes a character as input and returns the corresponding coded character
	// based on the code
	//********************************************************************
	public char encode(char c){
		int cPos = getCPos(c);
		return code.charAt(cPos);

	}

	//********************************************************************
	// Takes a character as input and returns the corresponding coded character
	// based on the inverted code
	//********************************************************************
	public char invert(char c){
		int cPos = getCPos(c);
		return invertedCode.charAt(cPos);	
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
