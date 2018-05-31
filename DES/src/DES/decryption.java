package DES;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class decryption {
	public static int Key2[][]=new int[8][7];
	public static int mingwen[][]=new int[8][8];
	public static ArrayList<int [][]>List=new ArrayList<int [][]>();
	public static String Operation(String miw,String Key) throws UnsupportedEncodingException{
		int miwen[][]=new int[8][8];//密文
		int miyao[][]=new int[8][7];//密钥
		miyao=BinToStr_Convert.StrKeyToInt(Key);
		String[]mi=miw.split("-");
		int sub=Integer.parseInt(mi[0]);
		//System.out.println(mi[1]);
		String miwe=BinToStr_Convert.HexStrToBin(mi[1]);
		//System.out.println(miwe);
		int length=miwe.length();
		int size=length/64;
		int flag=length%64;
		if(flag!=0){
			return"该密文有误，无法解密！！！！";
		}
		GetKey(miyao);//产生密钥
		String []str=new String[size];
		for(int i=0;i<size;i++){
			str[i]=miwe.substring(i*64,(i+1)*64);
		}
		String result="";
		for(int i=0;i<size;i++){
			int[]re=BinToStr_Convert.BinStrToIntArray(str[i]);
			for(int j=0;j<8;j++){
				for(int k=0;k<8;k++){
					miwen[j][k]=re[j*8+k];
				}
			}
			result=result+Des(miwen);
		}
		//System.out.println(result);
		result=result.substring(0,miwe.length()-sub);
		result=BinToStr_Convert.BinToStr(result);
		List.clear();
		return result;
		
	}

	public static String Des(int M[][]){
		int IP[][]={{58,50,42,34,26,18,10,2},
				    {60,52,44,36,28,20,12,4},
				    {62,54,46,38,30,22,14,6},
				    {64,56,48,40,32,24,16,8},
				    {57,49,41,33,25,17,9,1},
				    {59,51,43,35,27,19,11,3},
				    {61,53,45,37,29,21,13,5},
				    {63,55,47,39,31,23,15,7}};
		int IP_[][]={{40,8,48,16,56,24,64,32},
				     {39,7,47,15,55,23,63,31},
				     {38,6,46,14,54,22,62,30},
				     {37,5,45,13,53,21,61,29},
				     {36,4,44,12,52,20,60,28},
				     {35,3,43,11,51,19,59,27},
				     {34,2,42,10,50,18,58,26},
				     {33,1,41,9,49,17,57,25}};
		
		int S1[][]={{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
				    {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
				    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
				    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}};
		int S2[][]={{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
			        {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
			        {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
			        {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}};
		int S3[][]={{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
			        {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
			        {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
			        {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}};
		int S4[][]={{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
			        {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
			        {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
			        {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}};
		int S5[][]={{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
			        {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
			        {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
			        {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}};
		int S6[][]={{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
			        {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
			        {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
			        {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}};
		int S7[][]={{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
			        {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
			        {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
			        {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}};
		int S8[][]={{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
			        {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
			        {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
			        {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}};
		int P[][]={{16,7,20,21,29,12,28,17},
				   {1,15,23,26,5,18,31,10},
				   {2,8,24,14,32,27,3,9},
				   {19,13,30,6,22,11,4,25}};
		int L[][]=new int[4][8];
		int R[][]=new int[4][8];
		int temp[][]=new int[4][8];
		int kuochong[][]=new int[8][6];
		int exchange[][]=new int[8][4];
		int change[]=new int[32];
		int OriginKey[][]=new int[6][8];
		for(int i=0;i<4;i++){//将明文分两半
			for(int j=0;j<8;j++){
				int p1=(IP[i][j]-1)/8;//行
				int p2=(IP[i][j]-1)%8;//列
				int q1=(IP[i+4][j]-1)/8;//行
				int q2=(IP[i+4][j]-1)%8;//列
				L[i][j]=M[p1][p2];
				R[i][j]=M[q1][q2];
			}
		}
		
		
		for(int i=0;i<16;i++){
			//1.扩充
			int FinalKey[][]=new int[8][6];
			int n=0;//将数组变为一维
			for(int j=0;j<4;j++){
				for(int k=0;k<8;k++){
					temp[j][k]=R[j][k];
					change[n]=R[j][k];
					n++;
				}
			}
			n=0;
			kuochong[0][0]=change[31];
			kuochong[7][5]=change[0];
			for(int j=0;j<8;j++){
				for(int k=1;k<5;k++){
					kuochong[j][k]=change[n];
					n++;
				}
			}
			for(int j=1;j<8;j++){
				kuochong[j][0]=change[j*4-1];
			}
			for(int j=0;j<7;j++){
				kuochong[j][5]=change[(j+1)*4];//扩展完成
			}
			
			OriginKey=List.get(15-i);
			for(int j=0;j<8;j++){//将密钥从6行8列转换为6行8列
				for(int k=0;k<6;k++){
					int p1=(j*6+k)/8;//行
					int p2=(j*6+k)%8;//列
					FinalKey[j][k]=OriginKey[p1][p2];
				}
			}
			for(int j=0;j<8;j++){//与密钥异或
				for(int k=0;k<6;k++){
					int p=kuochong[j][k];
					kuochong[j][k]=p^FinalKey[j][k];
				}
			}
			
			for(int j=0;j<8;j++){
				int p1=kuochong[j][0]*2+kuochong[j][5];//行
				int p2=kuochong[j][1]*8+kuochong[j][2]*4+kuochong[j][3]*2+kuochong[j][4];//列
				int p3=0;
				if(j==0){
					p3=S1[p1][p2];
				}
				else if(j==1){
					p3=S2[p1][p2];
				}
				else if(j==2){
					p3=S3[p1][p2];
				}
				else if(j==3){
					p3=S4[p1][p2];
				}
				else if(j==4){
					p3=S5[p1][p2];
				}
				else if(j==5){
					p3=S6[p1][p2];
				}
				else if(j==6){
					p3=S7[p1][p2];
				}
				else if(j==7){
					p3=S8[p1][p2];
				}
				exchange[j]=Exchange(p3);
			}
			
			for(int j=0;j<4;j++){
				for(int k=0;k<8;k++){
					int p1=(P[j][k]-1)/4;
					int p2=(P[j][k]-1)%4;
					R[j][k]=exchange[p1][p2];
				}
			}
			
			for(int j=0;j<4;j++){
				for(int k=0;k<8;k++){
					int p=R[j][k];
					R[j][k]=p^L[j][k];
					L[j][k]=temp[j][k];
				}
			}
			
		}
		int mingwen2[][]=new int[8][8];
		for(int j=0;j<4;j++){
			for(int k=0;k<8;k++){
				mingwen2[j][k]=R[j][k];
				mingwen2[j+4][k]=L[j][k];
			}
		}
		
		for(int j=0;j<8;j++){
			for(int k=0;k<8;k++){
				int p1=(IP_[j][k]-1)/8;
				int p2=(IP_[j][k]-1)%8;
				mingwen[j][k]=mingwen2[p1][p2];
			}
		}
		StringBuffer result=new StringBuffer();
		for(int j=0;j<8;j++){
			for(int k=0;k<8;k++){
				result.append(mingwen[j][k]);
			}
		}
		return result.toString();
	}

	public static int[] Exchange(int h){//十进制转二进制
		int l[]=new int[4];
		int n=0;
		int sum;
		for(int i=h;i>=1;i=i/2){
			if(i%2==0){
				sum=0;
			}
			else{
				sum=1;
			}
			l[3-n]=sum;
			n++;
		}
		while(n<=3){
			l[3-n]=0;
			n++;
		}
		return l;
	}

	public static void GetKey(int [][]Key1){//获得密钥
		int rule[]={1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
		int PC1[][]={{57,49,41,33,25,17,9},
				     {1,58,50,42,34,26,18},
				     {10,2,59,51,43,35,27},
				     {19,11,3,60,52,44,36},
				     {63,55,47,39,31,23,15},
				     {7,62,54,46,38,30,22},
				     {14,6,61,53,45,37,29},
				     {21,13,5,28,20,12,4}};
		int PC2[][]={{14,17,11,24,1,5,3,28},
			         {15,6,21,10,23,19,12,4},
			         {26,8,16,7,27,20,13,2},
			         {41,52,31,37,47,55,30,40},
			         {51,45,33,48,44,49,39,56},
			         {34,53,46,42,50,36,29,32}};
		
		int Key3[][]=new int[8][7];
		
			for(int i=0;i<8;i++){  //PC-1
				for(int j=0;j<7;j++){
					int p1=(PC1[i][j]-1)/8;
					int p2=(PC1[i][j]-1)%8;
					Key2[i][j]=Key1[p1][p2];
				}
			}
			
      for(int m=0;m<16;m++){//循环16轮
    	  int finalKey[][]=new int[6][8];
    	int p=rule[m];//左移的次数
   		for(int i=0;i<4;i++){//左移
   			for(int j=0;j<7;j++){
   				if(i==0&&j==0){
   					if(p==1){
   						Key3[3][6]=Key2[i][j];
   						Key3[7][6]=Key2[i+4][j];
   					}
   					else if(p==2){
   						Key3[3][5]=Key2[i][j];
   						Key3[7][5]=Key2[i+4][j];
   					}
   				}
   				else if(i==0&&j==1){
   					if(p==1){
   						Key3[0][0]=Key2[i][j];
   						Key3[4][0]=Key2[i+4][j];
   					}
   					else if(p==2){
   						Key3[3][6]=Key2[i][j];
   				        Key3[7][6]=Key2[i+4][j];
   					}
   				}
   				else{
   					int q1=(i*7+j-p)/7;
   					int q2=(i*7+j-p)%7;
   					int q3=((i+4)*7+j-p)/7;
   					int q4=((i+4)*7+j-p)%7;
   					Key3[q1][q2]=Key2[i][j];
   					Key3[q3][q4]=Key2[i+4][j];
   				}
   				
   			}
   		}
   		for(int i=0;i<8;i++){
   			for(int j=0;j<7;j++){
   				Key2[i][j]=Key3[i][j];
   			}
   		}
   		
   		for(int i=0;i<6;i++){//得到最终的key PC-2
   			for(int j=0;j<8;j++){
   				int x1=(PC2[i][j]-1)/7;//行
   				int x2=(PC2[i][j]-1)%7;//列
   				finalKey[i][j]=Key3[x1][x2];
   			}
   		}
   		List.add(finalKey);
       }


	}
}
