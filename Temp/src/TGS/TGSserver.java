package TGS;

import generateKey.generateKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Clock.Clock;
import DES.DesOperation;

public class TGSserver {
	String Ktgs;
	String Kv;
	ServerSocket Server;
	public TGSserver(){Ktgs="s222222";Kv="s333333";}
	void startServer() throws IOException {
		Server = new ServerSocket(2222);
		System.out.println("Waiting...");

		while (true) {
			Socket socket = Server.accept();
			System.out.println("客户端接入AS，端口号："+socket.getPort());
			ThreadTGS m = new ThreadTGS(socket);
			m.start();
		}
	}
	public static void main(String[] args) throws IOException{
		TGSserver srv=new TGSserver();
		srv.startServer();

	}
	class ThreadTGS extends Thread{
		Socket socket;
		ThreadTGS(Socket socket) {
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
				String[]temp=readClient.split("-");
				StringBuilder ticket_tgs_builder=new StringBuilder(temp[0].substring(10, 12));
				ticket_tgs_builder.append("-");
				ticket_tgs_builder.append(temp[1].substring(0, temp[1].length()-2));
				String ticket_tgs=ticket_tgs_builder.toString();
				String pre_ticket_tgs=DesOperation.DesDecryption(ticket_tgs, Ktgs);
				String Kc_tgs=pre_ticket_tgs.substring(0,7);
				System.out.println("ticket_tgs解密为:"+pre_ticket_tgs);
				String Kc_v=generateKey.getKey(7);
				String IDc=pre_ticket_tgs.substring(7, 11);
				String ADc=socket.getInetAddress().getHostAddress().toString();//?????????????????
				String IDv="S003";
				String TS4=Clock.getTime();
				String Lifetime4="60";
				StringBuilder ticket_v_builder=new StringBuilder(Kc_v);
				ticket_v_builder.append(IDc);
				ticket_v_builder.append(ADc);
				ticket_v_builder.append(IDv);
				ticket_v_builder.append(TS4);
				ticket_v_builder.append(Lifetime4);
				String ticket_v=DesOperation.DesEncryption(ticket_v_builder.toString(), Kv);
				StringBuilder data_builder=new StringBuilder(Kc_v);
				data_builder.append(IDv);
				data_builder.append(TS4);
				data_builder.append(ticket_v);
				String data=DesOperation.DesEncryption(data_builder.toString(), Kc_tgs);
				StringBuilder writeClient_builder=new StringBuilder("000100");
				writeClient_builder.append(data);
				String writeClient=writeClient_builder.toString();
				bw.writeUTF(writeClient);
				sr1.close();
				sr2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}