package DES;

import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String []args) throws UnsupportedEncodingException{
		//String a="hello,this is a message from �й��� ";
    	String a="hello,this is �ҵ��й��� ������������";
    	String Key="abcdxfg";
    	String key2="aaaaaaa";
		/*int [][]miyao={{0,1,1,0,0,1,0},
			       {0,0,1,0,1,0,1},
			       {1,0,0,1,1,0,0},
			       {1,1,1,0,0,1,0},
			       {0,0,0,1,1,1,1},
			       {1,1,0,1,0,0,0},
			       {1,0,0,0,1,1,0},
			       {0,0,1,1,0,1,0}};*/
		
	String miwen=DesOperation.DesEncryption(a, Key);
	System.out.println("���������ת��Ϊ����Ϊ:\n"+miwen);
	System.out.println("����������ת��Ϊ����Ϊ:\n"+DesOperation.DesDecryption(miwen, key2));
	System.out.println("����������ת��Ϊ����Ϊ:\n"+DesOperation.DesDecryption(miwen, Key));
	System.out.println("����������ת��Ϊ����Ϊ:\n"+DesOperation.DesDecryption(miwen, key2));
	}
}
