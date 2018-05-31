package chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class frame4 extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Socket socket1,socket2,socket3;
	private static String Pk;
	private static JFrame myframe=new JFrame("Login");
	private static JButton button1=new JButton("重置");
	private static JButton button2=new JButton("登录");
	private static JTextField name=new JTextField(22);
	private static JTextArea Key=new JTextArea();
	private static JTextArea process=new JTextArea();
	public static void main(String []args){
		//Pk=Pkey;
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel(); 
		northPanel.setLayout(new GridLayout(4,1,5,5));
		
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();
		
		JPanel southPanel=new JPanel(new BorderLayout()); 
		southPanel.setBorder(new TitledBorder("验证过程"));  
		southPanel.setPreferredSize(new Dimension(600,120));
		
		
		JLabel label=new JLabel("登录界面");
		JLabel label1=new JLabel("用户名");
		JLabel label2=new JLabel("密    钥");
		
		Key.setPreferredSize(new Dimension(250,20));
		
		process.setLineWrap(true);
	    process.setWrapStyleWord(true);
	    southPanel.add(new JScrollPane(process),"Center");
		
		panel1.add(label1);
		panel1.add(name);
		panel2.add(label2);
		panel2.add(Key);
		panel3.add(button1);
		panel3.add(button2);
		panel4.add(label);
		
		
		northPanel.add(panel4);
		northPanel.add(panel1);
		northPanel.add(panel2);
		northPanel.add(panel3);
		
		
		
		 myframe.setLayout(new BorderLayout());  
	     myframe.add(northPanel, "Center");  
	     myframe.add(southPanel, "South");  
	     myframe.setSize(600,400);  
	     myframe.setVisible(true); 
	     myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Socket getSock(){
		return socket3;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==button1){
			name.setText("");
			Key.setText("");
		}
		/*else if(event.getSource()==button2){
			try {
				socket1 = new Socket("localhost",1111);//与AS连接
				OutputStream out1 = socket1.getOutputStream();
				DataOutputStream bw1 = new DataOutputStream(out1);
				InputStream In1 = socket2.getInputStream();
				DataInputStream br1 = new DataInputStream(In1);
				bw1.writeUTF("请求TGS的票");
				process.setText("c->AS: 请求TGS票据\n");
				String a1=br1.readUTF();
				process.append("AS->c:"+a1+"\n");
				socket1.close();
				
				socket2 = new Socket("localhost",2222);//与TGS连接
				OutputStream out2 = socket1.getOutputStream();
				DataOutputStream bw2 = new DataOutputStream(out2);
				InputStream In2 = socket2.getInputStream();
				DataInputStream br2 = new DataInputStream(In2);
				bw2.writeUTF("请求ChatServer的票");
				process.append("c->TGS: 请求ChatServer票据\n");
				String a2=br2.readUTF();
				process.append("c->TGS:"+a2+"\n");
				socket2.close();
				
				socket2 = new Socket("localhost",3333);//与Server连接
				OutputStream out3 = socket3.getOutputStream();
				DataOutputStream bw3 = new DataOutputStream(out3);
				InputStream In3 = socket2.getInputStream();
				DataInputStream br3 = new DataInputStream(In3);
				bw3.writeUTF("请求ChatServer的服务");
				process.append("c->V: 请求ChatServer的服务\n");
				String a3=br3.readUTF();
				process.append("V->c:"+a3+"\n");
				bw3.writeUTF("1-"+name.getText()+"-"+Pk);//将公钥上传给chat服务器
				new frame1(socket3,name.getText());
				myframe.dispose();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}}
