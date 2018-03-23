
public class Encrypter {
	public static String encrypt(String message){
		String encryptedMessage = "";
		for(int i=0; i<message.length(); i++){
			char each = message.charAt(i) ;
			if((each>='A' && each<='Z') || (each>='a' && each<='z'))
				each += 1;
			encryptedMessage += (each);
		}
		return encryptedMessage;
	}
	
	public static String decrypt(String message){
		String decryptedMessage = "";
		for(int i=0; i<message.length(); i++){
			char each = message.charAt(i) ;
			if((each>='A' && each<='Z') || (each>='a' && each<='z'))
				each -= 1;
			decryptedMessage += (each);
		}
		return decryptedMessage;
	}
}
