//*************************************************************************
// PlugBoard.java       Author: Huff                     5/6/2016
//
// Models the functionality of the Enigma plugboard
//*************************************************************************
public class PlugBoard {
	private String config;
	private String inverseConfig;
	private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String PRESET_1 = "QWERTYUIOPASDFGHJKLZXCVBNM";
	private final String PRESET_2 = "MNBVCXZLKJHGFDSAPOIUYTREWQ";
	private final String PRESET_3 = "AZERTYUIOPQSDFGHJKLMWXCVBN";
	private final String PRESET_4 = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
	private final String PRESET_5 = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
	private final String PRESET_6 = "AEIOUYBCDFGHJKLMNPQRSTVWXZ";

	//********************************************************************
	// Constructor, creates a plugboard with a given configuration and
	// sets the inverse configuration
	//********************************************************************
	public PlugBoard(String s){
		config = s;
		inverseConfig();
	}

	//********************************************************************
	// Constructor, creates a plugboard with the configuration being the 
	// alphabet
	//********************************************************************
	public PlugBoard(){
		config = ALPHABET;
		inverseConfig = ALPHABET;
	}

	//********************************************************************
	// Config accessor
	//********************************************************************
	public String getConfig(){
		return config;
	}

	//********************************************************************
	// Sets the configuration to the alphabet
	//********************************************************************
	public void setDefaultConfig(){
		config = ALPHABET;
	}

	//********************************************************************
	// Takes a string as input, determines if the string is a vaild configuration
	// and if so, sets the configuration to the input
	//********************************************************************
	public boolean setConfig(String s){
		if(s.length() != 26){
			return false;
		}

		boolean allLetters = s.chars().allMatch(x -> Character.isLetter(x));
		if(!allLetters){
			return false;
		}

		boolean noDuplicates = true;

		for(int i = 0; i < s.length(); i++)
			for(int j = 0; j < s.length(); j++){
				if(j != i){
					if(s.charAt(j) == s.charAt(i)){
						noDuplicates = false;
						return false;
					}
				}
			}

		if(allLetters && noDuplicates){
			config = s.toUpperCase();
			inverseConfig();
		}
		return true;
	}

	//********************************************************************
	// Sets the configuration to one of 6 preset configurations
	//********************************************************************
	public boolean setConfig(int num){
		switch (num){
		case 1: config = PRESET_1;
		inverseConfig();
		return true;
		case 2: config = PRESET_2;
		inverseConfig();
		return true;
		case 3: config = PRESET_3;
		inverseConfig();
		return true;
		case 4: config = PRESET_4;
		inverseConfig();
		return true;
		case 5: config = PRESET_5;
		inverseConfig();
		return true;
		case 6: config = PRESET_6;
		inverseConfig();
		return true;
		default : return false;
		}
	}

	//********************************************************************
	// Takes a character as input and returns the corresponding character based
	// on the current configuration
	//********************************************************************
	public char changeIn(char c){
		int pos = getCPos(c);
		return config.charAt(pos);
	}

	//********************************************************************
	// Takes a character as input and returns the corresponding character based
	// on the current inverse configuration
	//********************************************************************
	public char changeOut(char c){
		int pos = getCPos(c);
		return inverseConfig.charAt(pos);
	}

	//********************************************************************
	//  Returns a character's position in the alphabet
	//********************************************************************
	private int getCPos(char c){
		boolean found = false;
		int cPos = -1;
		for(int i = 0; i <= 25 && !found; i++){
			if(ALPHABET.charAt(i) == c){
				cPos = i;
				found = true;
			}
		}

		return cPos;
	}

	//********************************************************************
	// Determines and sets the inverse configuration based on the current 
	// configuration
	//********************************************************************
	private void inverseConfig(){
		String inverse = "";
		for(int i = 0; i <= 25; i++){
			for(int j = 0; j <= 25; j++){
				if(ALPHABET.charAt(i) == config.charAt(j)){
					inverse = inverse + ALPHABET.charAt(j);
				}
			}
		}

		inverseConfig = inverse;
	}
}
