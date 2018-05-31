package rsa;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Convert {
	public static String StrToBin(String s) throws UnsupportedEncodingException{
		  byte[] bytes = s.getBytes();
		  StringBuilder binary = new StringBuilder();
		  for (byte b : bytes)
		  {
		     int val = b;
		     for (int i = 0; i < 8; i++)
		     {
		        binary.append((val & 128) == 0 ? 0 : 1);
		        val <<= 1;
		     }
		  }
		  
		  String info=binary.toString();
		  return info;
    }
	
	public static BigInteger BinToBig(String BinStr){
		return new BigInteger(BinStr,2);
	}
	
	public static String BigToBin(BigInteger Big){
		String result=Big.toString(2);
		int length=result.length();
		int temp=length%8;
		if(temp!=0){
			for(int i=0;i<8-temp;i++){
				result="0"+result;
			}
		}
		return result;
	}
	
	public static String BinToStr(String hex2Str) throws UnsupportedEncodingException{
		int limit = 8;
		int end = limit;
		int len=hex2Str.length()/8;
		String []temp=new String[len];
		int count=0;
		// 循环分隔，如果最后一次分隔不到分隔位数，则分隔之后跳出循环
		for (int start = 0; start < hex2Str.length();count++)
		{
		temp[count] = hex2Str.substring(start, end);
		start = end;
		// 分隔之后指针加分隔位数，下次循环分隔
		end = end + limit;
		}
		
			byte [] b = new byte[temp.length];
			for(int i = 0;i<b.length;i++)
			{
				b[i] = Long.valueOf(temp[i], 2).byteValue();
			}
			String text = new String(b, "GBK");
			return text;
		}
	
	
public static void main(String []args) throws UnsupportedEncodingException{
		String s="abchdgsj";
		String c=StrToBin(s);
		System.out.println(c);
		BigInteger x=BinToBig(c);
		System.out.println(x.toString());
		System.out.println(BigToBin(x));
		System.out.println(BinToStr(BigToBin(x)));
	}
	
}
