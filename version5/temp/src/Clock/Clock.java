package Clock;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Clock {
	public static String getTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm"); 
		String Time=df.format(System.currentTimeMillis());
		return Time;   
	}
	public static String getTime1()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��"); 
		String Time=df.format(System.currentTimeMillis());
		return Time;  
	}
	public static void main(String[] args) throws IOException{
		System.out.println(Clock.getTime1());
	}
	
}
