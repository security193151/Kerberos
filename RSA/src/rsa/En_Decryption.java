package rsa;

import java.math.BigInteger;

public class En_Decryption {
public static BigInteger En_Decryption(BigInteger words,BigInteger Key,BigInteger n){//�ı�����Կ����ģ��n
	return words.modPow(Key, n);
}
}
