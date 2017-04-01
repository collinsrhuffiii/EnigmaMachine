import java.util.Random;

public class Tester2 {
	
	private static String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static Random r = new Random();
	
	public static void main(String[] args){
		EnigmaMachine enigma = new EnigmaMachine();
		String message;
		String encodedMessage;
		String decodedMessage;
		boolean messageMatch;
		
		for(int i = 0; i <= 1000; i++){
			enigma.reset();
			message = randomString((int)(i*Math.random()));
			encodedMessage = enigma.encodeString(message);
			enigma.reset();
			decodedMessage = enigma.encodeString(encodedMessage);
			messageMatch = message.toUpperCase().equals(decodedMessage);
			if(i % 15 != 0){
				System.out.print(messageMatch);
			}
			else {
				System.out.println(messageMatch);
			}
		}
		
	}
	
	private static String randomString(int length){
	   StringBuilder sb = new StringBuilder(length);
	   for( int i = 0; i < length; i++ ) 
	      sb.append(characters.charAt(r.nextInt(characters.length())));
	   return sb.toString();
	}
}
