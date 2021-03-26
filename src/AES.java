/*******************************************************************
*	Member 1: Vipawan Jarukitpipat		ID: 6088044		Section: 1 *
*	Member 2: Klinton Chhun				ID: 6088111		Section: 1 *
********************************************************************/

/*
 * Note: For this encryption, we will use AES (Advanced Encryption Standard)
 * by using 256 key size and ECB ( Electronic Code Block ) with PKCS5 padding
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private SecretKeySpec secretKey;
	private byte[] keyByte;
	private String textA;
	private String key;
	
	public AES(String textA, String key) {
		this.textA = textA;
		this.key = key;
	}
	
	public void encryptKey(String key) {
		MessageDigest sha = null;
		try {
			keyByte = key.getBytes("UTF-8"); // key size 2^8 = 256 bits
			sha = MessageDigest.getInstance("SHA-1"); // SHA-1 is 160 bit has function that the same as MD5 algorithm
			keyByte = sha.digest(keyByte); 
			keyByte = Arrays.copyOf(keyByte, 16); // add 16 padding to keybyte
			secretKey = new SecretKeySpec(keyByte, "AES");
		}catch(NoSuchAlgorithmException error) {
			error.printStackTrace();
		}catch(UnsupportedEncodingException error) {
			error.printStackTrace();
		}
	}
	
	public String encrypt() {
		String encryptedText = "";
		try {
			encryptKey(key);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedText = Base64.getEncoder().encodeToString(cipher.doFinal(textA.getBytes("UTF-8")));
		}catch(Exception error) {
			error.printStackTrace();
		}
		return encryptedText;
	}
	
	public String decrypt() {
		String decryptedText = "";
		try {
			encryptKey(key);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(textA)));
		}catch(Exception error) {
			error.printStackTrace();
		}
		return decryptedText;
	}
	
	public static void main(String[] args) {
		String key = "044&111";
		String str = "Ampere And Klinton AES";
		String enc = new AES(str, key).encrypt();
		System.out.println(enc);
		System.out.println(new AES(enc, key).decrypt());
	}
}
