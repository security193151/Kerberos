package DES;

import java.io.UnsupportedEncodingException;

public class BinToStr_Convert {
	
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
	
	public static int[] BinStrToIntArray(String binStr){
		String[]temp=binStr.split("");
		int []result=new int[temp.length];
		for(int i=0;i<temp.length;i++){
			result[i]=Integer.parseInt(temp[i]);
		}
		return result;
	}
	
	public static String BinToHexStr(String binaryStr) {  //二进制字符串转十六进制
		  
        if (binaryStr == null || binaryStr.equals("") || binaryStr.length() % 4 != 0) {  
            return null;  
        }  
  
        StringBuffer sbs = new StringBuffer();  
        // 二进制字符串是4的倍数，所以四位二进制转换成一位十六进制  
        for (int i = 0; i < binaryStr.length() / 4; i++) {  
            String subStr = binaryStr.substring(i * 4, i * 4 + 4);  
            String hexStr = Integer.toHexString(Integer.parseInt(subStr, 2));  
            sbs.append(hexStr);  
        }  
  
        return sbs.toString();  
    }  
	
	public static String HexStrToBin(String hexString) {  //十六进制转二进制
		  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        StringBuffer sb = new StringBuffer();  
        // 将每一个十六进制字符分别转换成一个四位的二进制字符  
        for (int i = 0; i < hexString.length(); i++) {  
            String indexStr = hexString.substring(i, i + 1);  
            String binaryStr = Integer.toBinaryString(Integer.parseInt(indexStr, 16));  
            while (binaryStr.length() < 4) {  
                binaryStr = "0" + binaryStr;  
            }  
            sb.append(binaryStr);  
        }  
  
        return sb.toString();  
    }   
	
	public static int[][] StrKeyToInt(String strKey) {  //密钥转int型
		 byte[] bytes = strKey.getBytes();
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
		  int [][]key=new int[8][7];
		  for(int i=0;i<8;i++)
		  {
			  for(int j=0;j<7;j++)
			  {
				  key[i][j]=Integer.parseInt(String.valueOf(info.charAt(i*7+j)));
			  }
		  }
		  return key;

    }   
	
}
