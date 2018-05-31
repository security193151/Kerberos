package rsa;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Test {
public static void main(String[]args) throws UnsupportedEncodingException{
	String ASk="168229418948822007504729356527397582096906571489";
	String An="637518875312186186297992106023559874024026440423";
	String BSk="268689026073514803364934080524996585302325759233";
	String Bn="1187555482990284574327469463255428485769221145069";
	String Text="aghddug";
	String miwen=RSA.RsaEncryption_Sig(Text, new BigInteger(An), new BigInteger(Bn), new BigInteger(ASk));
	System.out.println("得到的密文："+miwen);
	String mingwen=RSA.RsaDecryption_Ver(miwen, new BigInteger(Bn), new BigInteger(An), new BigInteger(BSk));
	System.out.println("得到的明文："+mingwen);
}
}
