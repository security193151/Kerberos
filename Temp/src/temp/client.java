package temp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
private static String Pk;//��Կ
private String Sk;//˽Կ
public client(){
	Pk="12345";
	Sk="45678";
}
public static void main(String []args) throws UnknownHostException, IOException{
	frame4 Myframe=new frame4(Pk);
}
}
