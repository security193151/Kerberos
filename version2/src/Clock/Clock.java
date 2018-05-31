package Clock;

import java.text.SimpleDateFormat;

public class Clock {
	public static String getTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm"); 
		String Time=df.format(System.currentTimeMillis());
		return Time;   
	}
}
