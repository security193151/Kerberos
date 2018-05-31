package temp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TGSserver {
	ServerSocket Server;
	public TGSserver(){}
	void startServer() throws IOException {
		Server = new ServerSocket(2222);
		System.out.println("Waiting");

		while (true) {
			Socket socket = Server.accept();
			System.out.println(socket.getInetAddress() + "������TGS������");
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
				String a=br.readUTF();
				System.out.println(a);
				sleep(3);
				bw.writeUTF("1-ticket");
				sleep(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}

