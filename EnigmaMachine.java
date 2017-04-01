//*************************************************************************
// EnigmaMachine.java       Author: Huff                     5/6/2016
//
// Models the functionality of the entire Enigma Machine
//*************************************************************************
public class EnigmaMachine {
	private Rotor fastRotor, midRotor, slowRotor;
	private Reflector reflector;
	private PlugBoard plug;
	private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	//********************************************************************
	// Default constructor, creates an Enigma Machine with default settings
	//********************************************************************
	public EnigmaMachine(){
		fastRotor = new Rotor(1);
		midRotor = new Rotor(2);
		slowRotor = new Rotor(3);
		reflector = new Reflector('B');
		plug = new PlugBoard();
	}

	//***************************************************************************
	// Constructor, creates an enigma machine with specified rotors and plugboard
	//***************************************************************************
	public EnigmaMachine(int r1, int r2, int r3, char c){
		this.fastRotor = new Rotor(r1);
		this.midRotor = new Rotor(r2);
		this.slowRotor = new Rotor(r3);
		plug = new PlugBoard();
		reflector = new Reflector(c);
	}

	//********************************************************************
	// Fast rotor accessor
	//********************************************************************
	public Rotor getFastRotor(){
		return fastRotor;
	}

	//********************************************************************
	// Mid rotor accessor
	//********************************************************************
	public Rotor getMidRotor(){
		return midRotor;
	}

	//********************************************************************
	// Slow rotor accessor
	//********************************************************************
	public Rotor getSlowRotor(){
		return slowRotor;
	}

	//********************************************************************
	// Fast rotor mutator
	//********************************************************************
	public void changeFastRotor(int r){
		this.fastRotor = new Rotor(r);
	}

	//********************************************************************
	// Mid rotor mutator
	//********************************************************************
	public void changeMidRotor(int r){
		this.midRotor = new Rotor(r);
	}

	//********************************************************************
	// Slow rotor mutator
	//********************************************************************
	public void changeSlowRotor(int r){
		this.slowRotor = new Rotor(r);
	}

	//********************************************************************
	// Plugboard accessor
	//********************************************************************
	public PlugBoard getPlugBoard(){
		return plug;
	}

	//********************************************************************
	// Reflector accessor
	//********************************************************************
	public Reflector getReflector(){
		return reflector;
	}

	//********************************************************************
	// Sets all rotor positions to 0
	//********************************************************************
	public void reset(){
		fastRotor.setRotorPosition(0);
		midRotor.setRotorPosition(0);
		slowRotor.setRotorPosition(0);
	}

	//********************************************************************
	// Returns the rotors to the settings of one previous step
	//********************************************************************
	public void previousSettings(){
		if(fastRotor.previousSettings())
			if(midRotor.previousSettings())
				slowRotor.previousSettings();
	}

	//********************************************************************
	// Takes a character as input and returns the encoded character based
	// on current settings
	//********************************************************************
	public char encodeChar(char c){
		c = Character.toUpperCase(c);
		if(c == ' '){
			return ' ';
		}

		if(getCPos(c) == -1){
			return c;
		}

		else {
			if(fastRotor.rotate())
				if(midRotor.rotate())
					slowRotor.rotate();

			int fastPos = fastRotor.getPosition();
			int midPos = midRotor.getPosition();
			int slowPos = slowRotor.getPosition();

			c = plug.changeIn(c);

			c = getChar(fastPos + getCPos(c));
			c = fastRotor.encode(c);
			c = getChar(getCPos(c) - (fastPos - midPos));
			c = midRotor.encode(c);
			c = getChar(getCPos(c) - (midPos - slowPos));
			c = slowRotor.encode(c);
			c = getChar(getCPos(c) + slowPos);

			c = reflector.reflect(c);

			c = getChar(getCPos(c) - slowPos);
			c = slowRotor.invert(c);
			c = getChar(getCPos(c) + (midPos - slowPos));
			c = midRotor.invert(c);
			c = getChar(getCPos(c) + (fastPos - midPos));
			c = fastRotor.invert(c);
			c = getChar(getCPos(c) - fastPos);

			c = plug.changeOut(c);

			return c;
		}
	}

	//********************************************************************
	// Takes a string as input and returns the encoded string
	//********************************************************************
	public String encodeString(String s){
		String message = "";
		for(int i = 0; i < s.length(); i++)
			message = message + encodeChar(s.charAt(i));
		return message;
	}

	//********************************************************************
	// Returns a character's postion in the alphabet
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
	// Takes an integer as input and returns the character corresponding to
	// that position
	//********************************************************************
	private char getChar(int pos){
		if(pos > 25){
			return ALPHABET.charAt(pos % ALPHABET.length());
		}

		if(pos < 0){
			return ALPHABET.charAt(ALPHABET.length() + pos);
		}

		else {
			return ALPHABET.charAt(pos);
		}	
	}
}
