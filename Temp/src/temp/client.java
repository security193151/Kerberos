package temp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
private static String Pk;//¹«Ô¿
private String Sk;//Ë½Ô¿
public client(){
	Pk="12345";
	Sk="45678";
}
public static void main(String []args) throws UnknownHostException, IOException{
	frame4 Myframe=new frame4(Pk);
}
}
