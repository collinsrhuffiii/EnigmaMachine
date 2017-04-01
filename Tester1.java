
public class Tester1 {
	public static void main(String[] args){

		EnigmaMachine enigma = new EnigmaMachine();
		EnigmaMachine enigma2 = new EnigmaMachine(1, 2, 3, 'B');
		
		enigma.getFastRotor().setRingSetting(12);
		enigma.getMidRotor().setRingSetting(7);
		enigma.getSlowRotor().setRingSetting(23);
		
		enigma.getFastRotor().setRotorPosition(0);
		enigma.getMidRotor().setRotorPosition(24);
		enigma.getSlowRotor().setRotorPosition(3);
		
		enigma.changeFastRotor(5);
		enigma.changeMidRotor(3);
		enigma.changeSlowRotor(1);
		
		enigma.getPlugBoard().setConfig(1);
		
		System.out.println(enigma.getFastRotor().getRotorCode());
		System.out.println(enigma.getMidRotor().getRotorCode());
		System.out.println(enigma.getSlowRotor().getRotorCode());
		System.out.println(enigma.getPlugBoard().getConfig());
		
		String message = "The quick brown fox jumped over the lazy dog";
		enigma.reset();
		String encodedMessage = enigma.encodeString(message);
		System.out.println(message);
		System.out.println(encodedMessage);
		enigma.reset();
		String decodedMessage = enigma.encodeString(encodedMessage);
		System.out.println(decodedMessage);
		System.out.println(message.toUpperCase().equals(decodedMessage));
	}	
}
