package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyChatServer {
	Map<String, Socket> clientList = new HashMap<String, Socket>();
	ServerSocket Server;
	public MyChatServer(){}
	void startServer() throws IOException {
		Server = new ServerSocket(2222);
		System.out.println("Waiting");

		while (true) {
			Socket socket = Server.accept();
			System.out.println(socket.getInetAddress() + "已连接服务器");
			ThreadChat m = new ThreadChat(socket);
			m.start();
		}
	}
	public static void main(String[] args) throws IOException{
		MyChatServer srv=new MyChatServer();
		srv.startServer();
		
		
		
}

class ThreadChat extends Thread {
	Socket socket;
	OutputStream sr1;
	InputStream sr2 ;
    int count=0;
	ThreadChat(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			sr1 = socket.getOutputStream();//创建流传输
			DataOutputStream bw = new DataOutputStream(sr1);
			sr2 = socket.getInputStream();
			DataInputStream br = new DataInputStream(sr2);
			 while(true){
				 if(socket.isClosed() == false && socket.isConnected() == true){
					String mess=br.readUTF();//获取输入流
					 String[]string1 = mess.split("-");
					 int NO=Integer.parseInt(string1[0]);
					 switch(NO){
					 case 1:
						 if(canlogin(string1[1],string1[2])==true){
							 clientList.put(string1[1],socket);
							 bw.writeInt(1);
							 sendList();
						 }
						 else if(canlogin(string1[1],string1[2])==false){
							 bw.writeInt(0);
							 bw.writeUTF("登陆失败，请进行注册或是重输信息");
						 }
						 System.out.println("denglu ok!");
						 break;
					 case 2:
						 if(exist(string1[1],string1[2])==true){
							 bw.writeInt(0);
							 bw.writeUTF("注册失败，该用户名已注册");
						 }
						 else if(canlogin(string1[1],string1[2])==false){
							 zhuce(string1[1],string1[2]);
							 bw.writeInt(1);
							 bw.writeUTF("注册成功，赶快去登录吧");
						 }
						 System.out.println("register ok!");
						 break;
					 case 3:
						 sendAll("\n"+string1[1]+":"+string1[2]);
						 System.out.println("information start!");
						 break;
					 case 4:
						 Socket sock=clientList.remove(string1[1]);
						 sendList();
						 break;
					 case 5:
						 Socket sock1=clientList.get(string1[2]);
						 if(sock1!=null){
							 OutputStream s = sock1.getOutputStream();
							DataOutputStream w = new DataOutputStream(s);
							w.writeUTF("2"+"-"+string1[1]+" wanna have a private conversation with you,check you have connected");
							bw.writeUTF("3"+"-"+"1");
						 }
						 else{
								bw.writeUTF("3"+"-"+"0");
						 }
						 break;
					 case 6:
						 Socket sock2=clientList.get(string1[2]);
						 OutputStream s1= sock2.getOutputStream();
							DataOutputStream w1 = new DataOutputStream(s1);
						 Date Time = new Date( );
							SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd  hh:mm:ss E a ");
							bw.writeUTF("4"+"-"+ft.format(Time)+"\n"+string1[1]+":"+string1[3]);
							w1.writeUTF("4"+"-"+ft.format(Time)+"\n"+string1[1]+":"+string1[3]);
							break;
					 }

					
					
					
					bw.flush();
					//br.close();
					//bw.close();
					//sr1.close();
					//sr2.close();
					//sleep(1);
					
				
			 }
		       }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
      
      
}
	boolean canlogin(String name,String passwd){
		Connection con=null;
		Statement stmt=null;
		//PreparedStatement pts=null;
		ResultSet rs=null;
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection("jdbc:Access:///G:/Access_document/PMS1.accdb");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select*from USER");
			//pts=con.prepareStatement("delete from OnlineUser where username=?");
			while(rs.next()){
				if(rs.getString("username").equals(name)&&rs.getString("password").equals(passwd)){
					return true;
				}
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//连接数据库
		return false;
	}
	
	
	boolean exist(String name,String passwd){
		Connection con=null;
		Statement stmt=null;
		//PreparedStatement pts=null;
		ResultSet rs=null;
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection("jdbc:Access:///G:/Access_document/PMS1.accdb");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select*from USER");
			//pts=con.prepareStatement("delete from OnlineUser where username=?");
			while(rs.next()){
				count=rs.getInt("ID");
				if(rs.getString("username").equals(name)){
					return true;
				}
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//连接数据库
		return false;
	}
	
	
	void zhuce(String name,String passwd){
			Connection con=null;
			PreparedStatement stmt=null;
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				con=DriverManager.getConnection("jdbc:Access:///G:/Access_document/PMS1.accdb");
				
				stmt = con.prepareStatement("INSERT INTO USER VALUES(?,?,?);");
	            stmt.setInt(1, count+1);
	            stmt.setString(2, name);
	            stmt.setString(3, passwd);
	            stmt.execute();
	            count=0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//连接数据库
		}
	
	
	synchronized void sendAll(String mess) throws IOException{
		 Date Time = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd  hh:mm:ss E a ");
			Collection<Socket> collection =clientList.values();
			Iterator<Socket> iterator = collection.iterator();
			// 从while循环中读取value
			while(iterator.hasNext()){
			Socket key = iterator.next(); // 此时的String类型的value就是我们需要的获取的值
			OutputStream sr = key.getOutputStream();
			DataOutputStream bw = new DataOutputStream(sr);
			 bw.writeUTF("2"+"-"+ft.format(Time)+mess);
			 bw.flush();
			}
			
	}
	synchronized void sendList() throws IOException{
		Set<String> setKey = clientList.keySet();
		Iterator<String> iterator = setKey.iterator();
		// 从while循环中读取key
		Collection<Socket> collection =clientList.values();
		Iterator<Socket> terator = collection.iterator();
		String temp="当前在线用户\n";
		while(iterator.hasNext()){
			String me = iterator.next();// 此时的String类型的key就是我们需要的获取的值
			 temp=temp+me+"\n";
		}
		while(terator.hasNext()){
			Socket key = terator.next(); // 此时的String类型的value就是我们需要的获取的值
			OutputStream sr = key.getOutputStream();
			DataOutputStream bw = new DataOutputStream(sr);
			bw.writeUTF("1"+"-"+temp);
			 bw.flush();
		 }
			
			
			
		
}
}
}
	

