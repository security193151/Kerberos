package RSA;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class RSA {
public static String RsaEncryption_Sig(String min,BigInteger Myn,BigInteger Otn,BigInteger MySk) throws UnsupportedEncodingException{
	//���ģ�����n������n������˽Կ��������ԿOthers'Pk
	String BinStr=Convert.StrToBin(min);
	BigInteger mingwen=Convert.BinToBig(BinStr);//����
	BigInteger Hash=new BigInteger(MD5.MD5(mingwen.toString()),16);
	BigInteger Sig=En_Decryption.En_Decryption(Hash,MySk,Myn);//ǩ��
	BigInteger OtPk=new BigInteger("65537");
	BigInteger En=En_Decryption.En_Decryption(mingwen, OtPk,Otn);
	int length=En.toString().length();
	String Temp=length+"-"+En.toString()+Sig.toString();//���ܺ�Ľ��
	return Temp;
}

public static String RsaDecryption_Ver(String mi,BigInteger Myn,BigInteger Otn,BigInteger MySk) throws UnsupportedEncodingException{
	String str[]=mi.split("-");
	int length1=Integer.parseInt(str[0]);//���ĳ���
	int length2=str[1].toString().length();
	String str1=str[1].substring(0, length1);//����
	String str2=str[1].substring(length1, length2);//ǩ��
	BigInteger str1_=En_Decryption.En_Decryption(new BigInteger(str1), MySk, Myn);//�õ�������
	BigInteger Sig=new BigInteger(str2);
	BigInteger Hash=new BigInteger(MD5.MD5(str1_.toString()),16);
	BigInteger OtPk=new BigInteger("65537");
	if(Hash.equals(En_Decryption.En_Decryption(Sig, OtPk, Otn))){
		System.out.println("ǩ����֤�ɹ�����������Ϣ�ɿ�����");
		String temp=Convert.BigToBin(str1_);
		String result=Convert.BinToStr(temp);
		return result;
	}
	else{
		System.out.println("ǩ����֤ʧ�ܣ���������Ϣ���ɿ�����");
		return null;
	}
	
}

}
