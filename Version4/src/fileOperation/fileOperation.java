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
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
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
		 File file = new File(fileName);//定义一个file对象，用来初始化FileReader
		 if(file.exists())
		 {
	        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
	        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
	        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
	        String s = "";
	        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
	            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
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
