package DES;

import java.io.UnsupportedEncodingException;

public class DesOperation {
	public static String DesEncryption(String mingwen,String Key) throws UnsupportedEncodingException{
		return encryption.Operation(mingwen, Key);
	}
	
	
	public static String DesDecryption(String miwen,String Key) throws UnsupportedEncodingException{
		return decryption.Operation(miwen, Key);
	}
}
