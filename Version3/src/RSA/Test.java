package RSA;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Test {
public static void main(String[]args) throws UnsupportedEncodingException{
	String ASk="984525387529824206546863807160703399643956858145";
	String An="1271035385756482724459497435172204207466105945727";
	String BSk="127760179010238107697078585939145709214191612033";
	String Bn="891695298380615001506225469695029722875464135973";
	String Text="aghddug";
	String miwen=RSA.RsaEncryption_Sig(Text, new BigInteger(An), new BigInteger(Bn), new BigInteger(ASk));
	System.out.println("得到的密文："+miwen);
	String mingwen=RSA.RsaDecryption_Ver(miwen, new BigInteger(Bn), new BigInteger(An), new BigInteger(BSk));
	System.out.println("得到的明文："+mingwen);
}
}
