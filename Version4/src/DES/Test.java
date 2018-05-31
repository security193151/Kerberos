package DES;

import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String []args) throws UnsupportedEncodingException{
		//String a="hello,this is a message from 中国！ ";
    	String a="hello,this is 我的中国心 啊啊啊啊啊啊";
    	String Key1="abcdxfg";
    	String K2="aaaaaaa";
		/*int [][]miyao={{0,1,1,0,0,1,0},
			       {0,0,1,0,1,0,1},
			       {1,0,0,1,1,0,0},
			       {1,1,1,0,0,1,0},
			       {0,0,0,1,1,1,1},
			       {1,1,0,1,0,0,0},
			       {1,0,0,0,1,1,0},
			       {0,0,1,1,0,1,0}};*/
		
	String miwen=DesOperation.DesEncryption(a, Key1);
	System.out.println("将以上语句转换为密文为:\n"+miwen);
	System.out.println("将以上语句转换为密文为:\n"+DesOperation.DesEncryption(a, K2));
	System.out.println("将以上密文转换为明文为:\n"+DesOperation.DesDecryption(miwen, K2));
	System.out.println("将以上语句转换为明文为:\n"+DesOperation.DesDecryption(miwen, Key1));
	}
}
