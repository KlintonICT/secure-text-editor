/*******************************************************************
*	Member 1: Vipawan Jarukitpipat		ID: 6088044		Section: 1 *
*	Member 2: Klinton Chhun				ID: 6088111		Section: 1 *
********************************************************************/

/* Note: For this VigenereCipher, instead of using only alphabet from A-Z, we will implement 
 * the encryption and decryption according to ASCII table which range from 32 to 126 because
 * the numbers that are out this range is just and keyboard notation, it's not character.
 * 
 * Main Formula to implement this:
 * encryption: Ci = ( Pi + Ki ) mod 95	// because from 32 to 126, we have 95 characters
 * decryption: Pi = ( Ci - Ki + 95 ) mod 95 
 */

public class VigenereCipher {
	private String textA;
	private String key;
	
	public VigenereCipher(String textA, String key) {
		this.textA = textA;
		this.key = key;
	}
	public String encrypt() {
		String Ci = "";
		for(int i = 0; i < textA.length(); i++) {
			int Pi = textA.charAt(i); // get ascii number from plaintext
			if(Pi < 32 || Pi > 126) { 
				Ci += (char) Pi; // still store the plaintext, just not convert it
				continue; // skip if plaintext are out range
			}
			int Ki = key.charAt(i % key.length()) - 32; // get position(nth) of key
			
//			System.out.println(key.charAt(i % key.length()));
//			System.out.println(key.charAt(i % key.length()) - 32);
			
			int summation = (Pi - 32) + Ki; // Pi - 32 because we want to know position(nth) of plaintext
			Ci += (char) (( summation % 95 ) + 32); // + 32 because we want to get the text back not the position
		}
		return Ci;
	}
	public String decrypt() {
		String Pi = "";
		for(int i = 0; i < textA.length(); i++) {
			int Ci = textA.charAt(i); 
			if(Ci < 32 || Ci > 126) { 
				Pi += (char) Ci; 
				continue; 
			}
			int Ki = key.charAt(i % key.length()) - 32; 
			int summation = (Ci - 32) + (95 - Ki); 
			Pi += (char) (( summation % 95 ) + 32); 
		}
		return Pi;
	}
	
	public static void main(String[] args) {
//		String key = "044&111";
//		String str = "Ampere And Klinton Vigenere Cipher";
		String key = "ICT";
		String str = "MAHIDOL";
		String enc = new VigenereCipher(str, key).encrypt();
		System.out.println(enc);
		System.out.println(new VigenereCipher(enc, key).decrypt());
	}
}
