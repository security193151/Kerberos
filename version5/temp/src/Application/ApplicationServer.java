package Application;

import fileOperation.fileOperation;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Clock.Clock;
import DES.DesOperation;

public class ApplicationServer {
	String Kv;
	ServerSocket Server;
	Map<String, Socket>ClientList = new HashMap<String, Socket>();//用户名，对应的Socket
	public ApplicationServer(){Kv="s333333";}
	void startServer() throws IOException {
		Server = new ServerSocket(3333);
		System.out.println("Waiting...");

		while (true) {
			Socket socket = Server.accept();
			System.out.println("客户端接入应用服务器，端口号："+socket.getPort());
			ThreadApplication m = new ThreadApplication(socket);
			m.start();
		}
	}
	public static void main(String[] args) throws IOException{
		ApplicationServer srv=new ApplicationServer();
		srv.startServer();

	}
	class ThreadApplication extends Thread{
		Socket socket;
		ThreadApplication(Socket socket) {
			this.socket = socket;
		}

		/*		boolean isConnected(){
			try{
				socket.sendUrgentData(0xFF);
				return true;
			}catch(Exception e){
				return false;
			}
		}
		 */
		public void run(){
			try {
				
				OutputStream sr1 = socket.getOutputStream();
				DataOutputStream bw = new DataOutputStream(sr1);
				InputStream sr2= socket.getInputStream();
				DataInputStream br = new DataInputStream(sr2);

				String readClient=br.readUTF();


				String[]temp=readClient.split("-");
				StringBuilder ticket_v_builder=new StringBuilder(temp[0].substring(6));
				ticket_v_builder.append("-");
				ticket_v_builder.append(temp[1].substring(0, temp[1].length()-2));
				String pre_ticket_v=DesOperation.DesDecryption(ticket_v_builder.toString(), Kv);
				System.out.println("ticket_v解密为:"+pre_ticket_v);
				String Kc_v=pre_ticket_v.substring(0, 7);
				String IDc=pre_ticket_v.substring(7,11);
				StringBuilder Authenticator_builder=new StringBuilder(temp[1].substring(temp[1].length()-2));
				Authenticator_builder.append("-");
				Authenticator_builder.append(temp[2].substring(0));
				String pre_Authenticator=DesOperation.DesDecryption(Authenticator_builder.toString(), Kc_v);
				System.out.println("Authenticator解密为:"+pre_Authenticator);
				String TS5=pre_Authenticator.substring(pre_Authenticator.length()-12);
				BigInteger mark = new BigInteger(TS5);
				mark=mark.add(BigInteger.ONE);
				String pre_data=mark.toString();
				//	System.out.println("TS5+1="+pre_data);
				String data=DesOperation.DesEncryption(pre_data, Kc_v);
				fileOperation.writeFile(Clock.getTime1()+"\r\n"+IDc+"接入,线程号:"+getName()+"\r\n\r\n","log.log");
				if(ClientList.containsKey(IDc))
				{
					String writeClient="111110";
					bw.writeUTF(writeClient);
					return;
				}
			
				StringBuilder writeClient_builder=new StringBuilder("000110");
				writeClient_builder.append(data);
				String writeClient=writeClient_builder.toString();
				bw.writeUTF(writeClient);

				StringBuilder onlineuser_builder=new StringBuilder("000111");

				for (String key : ClientList.keySet()) {
					onlineuser_builder.append(key);
				}
				bw.writeUTF(onlineuser_builder.toString());
				fileOperation.writeFile(Clock.getTime1()+"\r\n"+getName()+"(v->c):"+onlineuser_builder.toString()+"\r\n\r\n","log.log");

				StringBuilder onlineAlert =new StringBuilder("0010101");
				onlineAlert.append(IDc);

				for(String key:ClientList.keySet())
				{
					Socket s=ClientList.get(key);
					OutputStream s1 = s.getOutputStream();
					DataOutputStream bw1 = new DataOutputStream(s1);
					bw1.writeUTF(onlineAlert.toString());
					fileOperation.writeFile(Clock.getTime1()+"\r\n"+getName()+"(v->c):"+onlineAlert.toString()+"\r\n\r\n","log.log");
				}

				ClientList.put(IDc, socket);

				while(true)
				{

					String msgFromClient=br.readUTF();
					fileOperation.writeFile(Clock.getTime1()+"\r\n"+getName()+"(c->v):"+msgFromClient+"\r\n\r\n","log.log");
					String control=msgFromClient.substring(0, 6);
					switch(control)
					{
					case "001000":
					case "001001":
					case "111101":		
						Socket s=ClientList.get(msgFromClient.substring(10, 14));
						OutputStream s1 = s.getOutputStream();
						DataOutputStream bw1 = new DataOutputStream(s1);
						bw1.writeUTF(msgFromClient);

						break;
					case "001010":
						bw.writeUTF("001011");
						String offlineID=msgFromClient.substring(7,11);
						ClientList.remove(offlineID);
						StringBuilder offlineAlert=new StringBuilder("0010100");
						offlineAlert.append(offlineID);
						for(String key:ClientList.keySet())
						{
							Socket so=ClientList.get(key);
							OutputStream so1 = so.getOutputStream();
							DataOutputStream bw2 = new DataOutputStream(so1);
							bw2.writeUTF(offlineAlert.toString());
						}					
						break;
					}
					if(control.equals("001010"))
						break;
				}

				sr1.close();
				sr2.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}