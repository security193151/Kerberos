package rsa;

import java.math.BigInteger;

public class En_Decryption {
public static BigInteger En_Decryption(BigInteger words,BigInteger Key,BigInteger n){//文本，密钥，大模数n
	return words.modPow(Key, n);
}
}
