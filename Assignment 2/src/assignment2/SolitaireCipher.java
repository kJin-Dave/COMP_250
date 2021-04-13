package assignment2;


public class SolitaireCipher {
	public Deck key;
	
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}
	
	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = key.generateNextKeystreamValue();
		}
		return array;
	}
		
	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		msg = msg.replaceAll("[^A-Za-z]+", "").toUpperCase();
		String encoded = "";
		int[] keystream = getKeystream(msg.length());

		for (int i = 0; i < msg.length(); i++) {
			encoded = encoded +charRightShift(msg.charAt(i), keystream[i] );
		}

		return encoded;
	}

	private char charRightShift(char input, int n){

		int newPos = (input - 'A' + n)%26;
		return (char) ('A' + newPos);
		/*
	//Courtesy of week2 extra exercises
		//char cast
		char output = (char) (input+n);

		//if spills over
		if(input + n > 'Z') {
			output = charRightShift('A',input-'A'+n-26);
		}
		//if goes back (less than a)
		if (input +n < 'A') {
			output = charRightShift('Z', -(input-'A'-n-1));
		}
		return output;

		 */
	}

	private char charLeftShift(char input, int n){
		char output = (char) (input-n);

		//if spills over
		if(input - n > 'Z') {
			output = charLeftShift('A',n-('Z'-input-1));
		}
		//if goes back (less than a)
		if (input - n < 'A') {
			output = charLeftShift('Z', n-(input-'A'+1));
		}
		return output;
	}


	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {

		String encoded = "";
		int length = msg.length();
		int[] keystream = getKeystream(length);

		for (int i = 0; i < length; i++) {
			encoded = encoded + charLeftShift(msg.charAt(i), keystream[i] );
		}

		return encoded;
	}
	
}
