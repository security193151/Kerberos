package AS;

import DataBase.DataBase;
import generateKey.generateKey;
import DES.DesOperation;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import Clock.Clock;

public class ASserver {
	ServerSocket Server;
	public ASserver(){}
	void startServer() throws IOException {
		Server = new ServerSocket(1110);
		System.out.println("Waiting...");

		while (true) {
			Socket socket = Server.accept();
			System.out.println("客户端接入AS，端口号："+socket.getPort());
			ThreadAS m = new ThreadAS(socket);
			m.start();
		}
	}
	
	
	
	public static void main(String[] args) throws IOException{
		ASserver srv=new ASserver();
		srv.startServer();

	}
	class ThreadAS extends Thread{
		Socket socket;
		ThreadAS(Socket socket) {
			this.socket = socket;
		}
		


		public void run(){
			try {
				
				OutputStream sr1 = socket.getOutputStream();
				DataOutputStream bw = new DataOutputStream(sr1);
				InputStream sr2= socket.getInputStream();
				DataInputStream br = new DataInputStream(sr2);
				String readClient=br.readUTF();
			//	System.out.println(readClient);

				String KC=DataBase.search(readClient.substring(6, 10));
				if(KC==null)
				{
					bw.writeUTF("111111");
				}
				else
				{
					String kc_tgs = generateKey.getKey(7);
					String IDc=readClient.substring(6, 10);
					String ADc=socket.getInetAddress().getHostAddress().toString();//?????????????????????????
					System.out.println(ADc);//???????????????????????????????????
					String IDtgs="S002";
					String TS2=Clock.getTime();
					String Lifetime2="60";
					StringBuilder ticket_builder=new StringBuilder(kc_tgs);
					ticket_builder.append(IDc);
					ticket_builder.append(ADc);
					ticket_builder.append(IDtgs);
					ticket_builder.append(TS2);
					ticket_builder.append(Lifetime2);
					String pre_ticket=ticket_builder.toString();//////////////////////////////////
					String ktgs=DataBase.search(IDtgs);
					
					String ticket=DesOperation.DesEncryption(pre_ticket, ktgs);
				//	System.out.println(pre_ticket);
					
					StringBuilder data_builder=new StringBuilder(kc_tgs);
					data_builder.append(IDtgs);
					data_builder.append(TS2);
					data_builder.append(Lifetime2);
					data_builder.append(ticket);
					
					String pre_data=data_builder.toString();
					String data=DesOperation.DesEncryption(pre_data, KC);
				//	System.out.println("KC:"+KC);
					StringBuilder writeClient_builder=new StringBuilder("000010");
					writeClient_builder.append(data);
					
					String writeClient=writeClient_builder.toString();
					
					
					bw.writeUTF(writeClient);
					
				
				}
				sr1.close();
				sr2.close();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}
}