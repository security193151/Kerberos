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
		// ѭ���ָ���������һ�ηָ������ָ�λ������ָ�֮������ѭ��
		for (int start = 0; start < hex2Str.length();count++)
		{
		temp[count] = hex2Str.substring(start, end);
		start = end;
		// �ָ�֮��ָ��ӷָ�λ�����´�ѭ���ָ�
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
	
	public static String BinToHexStr(String binaryStr) {  //�������ַ���תʮ������
		  
        if (binaryStr == null || binaryStr.equals("") || binaryStr.length() % 4 != 0) {  
            return null;  
        }  
  
        StringBuffer sbs = new StringBuffer();  
        // �������ַ�����4�ı�����������λ������ת����һλʮ������  
        for (int i = 0; i < binaryStr.length() / 4; i++) {  
            String subStr = binaryStr.substring(i * 4, i * 4 + 4);  
            String hexStr = Integer.toHexString(Integer.parseInt(subStr, 2));  
            sbs.append(hexStr);  
        }  
  
        return sbs.toString();  
    }  
	
	public static String HexStrToBin(String hexString) {  //ʮ������ת������
		  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        StringBuffer sb = new StringBuffer();  
        // ��ÿһ��ʮ�������ַ��ֱ�ת����һ����λ�Ķ������ַ�  
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
	
	public static int[][] StrKeyToInt(String strKey) {  //��Կתint��
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
