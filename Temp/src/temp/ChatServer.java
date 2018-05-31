package temp;
/*
1-用户名-公钥
2-聊天对象,请求其公钥
3-发送对象-对称钥
4-发送对象-确认消息
5-发送对象-原对象-对话
6-用户名-登录票据
7-用户名   退出登录
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatServer {
	    Map<String, String>KeyList = new HashMap<String, String>();//用户名，对应的公钥
	    Map<String, Socket>ClientList = new HashMap<String, Socket>();//用户名，对应的Socket
		ServerSocket Server;
		public ChatServer(){}
		void startServer() throws IOException {
			Server = new ServerSocket(3333);
			System.out.println("Waiting");

			while (true) {
				Socket socket = Server.accept();
				System.out.println(socket.getInetAddress() + "已连接聊天服务器");
				ThreadChat m = new ThreadChat(socket);
				m.start();
			}
		}
		public static void main(String[] args) throws IOException{
			ChatServer srv=new ChatServer();
			srv.startServer();
			
			
			
	}
		
		
		class ThreadChat extends Thread{
			Socket socket;
			OutputStream sr1;
			InputStream sr2 ;
		    int count=0;
			ThreadChat(Socket socket) {
				this.socket = socket;
			}
			
			public void run(){
				try {
					sr1 = socket.getOutputStream();
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
								 KeyList.put(string1[1], string1[2]);
								 break;
							 case 2:
								 String Key=KeyList.get(string1[1]);//获得对方的公钥
								 bw.writeUTF("2-"+Key);
								 break;
							 case 3:
								 Socket sock=ClientList.get(string1[1]);
								 OutputStream s1= sock.getOutputStream();
							     DataOutputStream w1 = new DataOutputStream(s1);
							     w1.writeUTF(mess);
								 break;
								 
							 case 4:
								 Socket sock1=ClientList.get(string1[1]);
								 OutputStream s2= sock1.getOutputStream();
							     DataOutputStream w2 = new DataOutputStream(s2);
							     w2.writeUTF(mess);
								 break;
							 case 5:
								 Socket sock2=ClientList.get(string1[1]);
								 Socket sock3=ClientList.get(string1[2]);
								 OutputStream s3= sock2.getOutputStream();
							     DataOutputStream w3 = new DataOutputStream(s3);
							     OutputStream s4= sock3.getOutputStream();
							     DataOutputStream w4 = new DataOutputStream(s4);
							     Date Time = new Date( );
								 SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd  hh:mm:ss E a ");
								 w3.writeUTF("5"+"-"+ft.format(Time)+"\n"+string1[2]+":"+string1[3]+"\n");
								 w4.writeUTF("5"+"-"+ft.format(Time)+"\n"+string1[2]+":"+string1[3]+"\n");
								 break;
							 case 6:
								 //验证票据
								 bw.writeUTF("服务器验证成功，可以登陆");
								 ClientList.put(string1[1], socket);
								 sendList();
								 break;
							 case 7:
								 
								 break;
									 
							 }
						 
						 }
						}
					 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//创建流传输
				
		}
				

			}
		
		synchronized void sendList() throws IOException{
			Set<String> setKey = ClientList.keySet();
			Iterator<String> iterator = setKey.iterator();
			// 从while循环中读取key
			Collection<Socket> collection =ClientList.values();
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
				System.out.println(temp);
				bw.writeUTF("1"+"-"+temp);
				bw.flush();
			 }
			
				
				
				
			
	}


}

