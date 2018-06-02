package fileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class fileOperation {
	public static void writeFile(String msg,String fileName)
	{
		FileWriter fw = null;
		try {
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File f=new File(fileName);
			fw = new FileWriter(f, true);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(msg);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String readFile(String fileName) throws IOException 
	{
		 File file = new File(fileName);//����һ��file����������ʼ��FileReader
		 if(file.exists())
		 {
	        FileReader reader = new FileReader(file);//����һ��fileReader����������ʼ��BufferedReader
	        BufferedReader bReader = new BufferedReader(reader);//newһ��BufferedReader���󣬽��ļ����ݶ�ȡ������
	        StringBuilder sb = new StringBuilder();//����һ���ַ������棬���ַ�����Ż�����
	        String s = "";
	        while ((s =bReader.readLine()) != null) {//���ж�ȡ�ļ����ݣ�����ȡ���з���ĩβ�Ŀո�
	            sb.append(s + "\n");//����ȡ���ַ�����ӻ��з����ۼӴ���ڻ�����
	          //  System.out.println(s);
	        }
	        bReader.close();
	        String str = sb.toString();
	      //  System.out.println(str );

		return str;
		 }
		 else return null;
	}

	public static void main(String[] args) throws IOException {

		writeFile("lby\r\nlby","lby.txt");
		System.out.println(readFile("lby.txt"));

	}
}
