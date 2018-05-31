package chat;
/*
 1-用户名-密码      登录信息
 2-用户名-密码      注册信息
 3-用户名-消息      聊天信息
 4-用户名                 退出登录
 5-用户名-目的用户名        私聊连接
 6-用户名1-用户名2 私聊
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MyChatClient{
	private static Socket socket;
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		socket = new Socket("localhost",2222);
     Login lo=new Login(socket);
	}
}
class Login extends JFrame implements ActionListener{
	private static Socket socket;
	JFrame myframe=new JFrame("Login");
	private JButton but1=new JButton("重置");
	private JButton but2=new JButton("登录");
	private JButton but3=new JButton("注册");
	private JTextField nam=new JTextField();
	private JTextField pas=new JTextField();
	public Login(Socket socket){
		this.socket=socket;
		//JFrame myframe=new JFrame("Login");
		//JFrame.setDefaultLookAndFeelDecorated(true);
		Container contentPane = myframe.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.CYAN);
		myframe.setSize(500,380);
		JLabel label1=new JLabel("登录界面");
		label1.setBounds(220,10,200,50);
		 JLabel label2=new JLabel("用户名");
		 label2.setBounds(90,85,50,60);
		    JLabel label3=new JLabel("密	     码");
		    label3.setBounds(90,145,50,60);
		    nam.setBounds(150,100,200,30);
		    pas.setBounds(150,160,200,30);
		    but1.setBounds(90,220,80,30);
		    but2.setBounds(180,220,80,30);
		    but3.setBounds(270,220,80,30);
		    contentPane.add(label1);
			contentPane.add(label2);
			contentPane.add(label3);
			contentPane.add(but1);
			contentPane.add(but2);
			contentPane.add(but3);
			contentPane.add(nam);
			contentPane.add(pas);
			but1.addActionListener(this);
			but2.addActionListener(this);
			but3.addActionListener(this);
			myframe.setVisible(true);
			myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==but1){
			nam.setText("");
			pas.setText("");
		}
		else if(event.getSource()==but2){
			try {
				//socket = new Socket("localhost",2222);
				OutputStream s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				InputStream s1=socket.getInputStream();
				DataInputStream br=new DataInputStream(s1);
				String str1,str2;
				str1=nam.getText();//得到用户名
				str2=pas.getText();//得到密码
				bw.writeUTF("1"+"-"+str1+"-"+str2);//传用户名，传密码
				int R=br.readInt();//服务器传过来信息，表示是否登录，若是登录传过来1，若是没登录传过来0
				if(R==0){//没登录进行弹窗
					String m3=new String(br.readUTF());
					JDialog dia=new JDialog();
					dia.setSize(200, 100);
					dia.setVisible(true);
					JOptionPane.showMessageDialog(dia,m3);
				}else if(R==1){//登录显示聊天界面
					new room(socket,str1);
					myframe.dispose();
				}
				bw.flush();
				//br.close();
				//bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(event.getSource()==but3){
			new gist(socket);
			myframe.dispose();
		}
	}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			JFrame.setDefaultLookAndFeelDecorated(true);
	     Login lo=new Login(socket);
		}
	
	
	

}

class gist extends JFrame implements ActionListener{
	private static Socket socket;
	
	JFrame myframe=new JFrame("Regist");
	private JButton but1=new JButton("重置");
	private JButton but2=new JButton("完成");
	private JButton but3=new JButton("进行登录");
	private JTextField nam=new JTextField();
	private JTextField pas=new JTextField();
	public gist(Socket socket){
		this.socket=socket;
		JFrame.setDefaultLookAndFeelDecorated(true);
		Container contentPane = myframe.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.CYAN);
		myframe.setSize(500,380);
		JLabel label1=new JLabel("注册界面");
		label1.setBounds(220,10,200,50);
		 JLabel label2=new JLabel("用户名");
		 label2.setBounds(90,85,50,60);
		    JLabel label3=new JLabel("密	     码");
		    label3.setBounds(90,145,50,60);
		    nam.setBounds(150,100,200,30);
		    pas.setBounds(150,160,200,30);
		    but1.setBounds(90,220,80,30);
		    but2.setBounds(270,220,80,30);
		    but3.setBounds(90,260,260,30);
		    contentPane.add(label1);
			contentPane.add(label2);
			contentPane.add(label3);
			contentPane.add(but1);
			contentPane.add(but2);
			contentPane.add(but3);
			contentPane.add(nam);
			contentPane.add(pas);
			but1.addActionListener(this);
			but2.addActionListener(this);
			but3.addActionListener(this);
			myframe.setVisible(true);
			myframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==but1){
			nam.setText("");
			pas.setText("");
		}
		else if(event.getSource()==but2){
			try {
				//socket = new Socket("localhost",2222);
				OutputStream s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				InputStream s1=socket.getInputStream();
				DataInputStream br=new DataInputStream(s1);
				String str1,str2;
				str1=nam.getText();//得到用户名
				str2=pas.getText();//得到密码
				bw.writeUTF("2"+"-"+str1+"-"+str2);//传用户名，传密码
				int R=br.readInt();//服务器传过来信息，表示是否注册，若是注册成功传过来1，若是注册失败传过来0
				if(R==0){//注册失败进行弹窗
					String m3=new String(br.readUTF());
					JDialog dia=new JDialog();
					dia.setSize(200, 100);
					dia.setVisible(true);
					JOptionPane.showMessageDialog(dia,m3);
				}else if(R==1){//注册成功弹窗
					String m3=new String(br.readUTF());
					JDialog dia=new JDialog();
					dia.setSize(200, 100);
					dia.setVisible(true);
					JOptionPane.showMessageDialog(dia,m3);
				}
				bw.flush();
				//br.close();
				//bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(event.getSource()==but3){
			new Login(socket);
			myframe.dispose();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     gist gi=new gist(socket);
	}
}


class room extends JFrame implements ActionListener{
	   static Socket socket;
    static String name;
    static int H;
    static int tag;
	OutputStream s2;
	 InputStream s1;
	 String[]str=new String[999];
	 int i=0;
	JFrame myframe=new JFrame("Mychat");
	JButton reset = new JButton("清空");  
    JButton send= new JButton("发送");  
    JButton pchat= new JButton("查看传送细节");
    JButton out= new JButton("退出登录");
    JTextArea message = new JTextArea();
    JTextArea onlinelist=new JTextArea();
    static JTextArea contentArea = new JTextArea();  
	public room(Socket socket,String name){
		this.name=name;
		this.socket=socket;
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel();  
	    JPanel southPanel=new JPanel(new BorderLayout());  
	    JScrollPane leftPanel;  
	    
	    
        contentArea.setEditable(false);  
        contentArea.setForeground(Color.blue);
        //contentArea.setPreferredSize(new Dimension(,30));
       
          
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        JPanel mesPanel=new JPanel(new BorderLayout());
        mesPanel.add(new JScrollPane(message),"Center");
        
        
        southPanel.setBorder(new TitledBorder("Send Message")); 
        JPanel southeastPanel=new JPanel(new GridLayout(4,1));
        southPanel.add(mesPanel, "Center");
        southPanel.add(southeastPanel, "East");
        southeastPanel.add(send);
        southeastPanel.add(reset);
        southeastPanel.add(pchat);
        southeastPanel.add(out);
        southPanel.setPreferredSize(new Dimension(600,120));
        
        onlinelist.setEditable(false);
        leftPanel = new JScrollPane(onlinelist);  
        leftPanel.setBorder(new TitledBorder("Online Person"));  
        
        JScrollPane rightPanel = new JScrollPane(contentArea);  
        rightPanel.setBorder(new TitledBorder("Message Box"));
        
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel);  
        centerSplit.setDividerLocation(100);  
        
        JLabel label1=new JLabel(name+",Welcome to MyChat",SwingConstants.CENTER);
        northPanel.add(label1);
        reset.addActionListener(this);
		send.addActionListener(this);
		pchat.addActionListener(this);
		out.addActionListener(this);
        myframe.setLayout(new BorderLayout());  
        myframe.add(northPanel, "North");  
        myframe.add(centerSplit, "Center");  
        myframe.add(southPanel, "South");  
        myframe.setSize(600, 400);  
        myframe.setVisible(true); 
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread thread = new Thread()
        		{
        	public void run()
        	{
        		while(true){
        			try {
        				tag = 0;
						s1=socket.getInputStream();
						DataInputStream br=new DataInputStream(s1);
						String mess=br.readUTF();
						String[]string = mess.split("-");
						int NO=Integer.parseInt(string[0]);
						switch(NO){
						case 1:
							if(mess!=null){
								onlinelist.setText(string[1]);
							}
							break;
						case 2:
							xianshi(string[1]);
							break;
						case 3:
							H=Integer.parseInt(string[1]);
							tag = 1;
							break;
						case 4:
							privateroom.xianshi(string[1]);
							break;
						}
						
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    				
          }
        	}
        		};
        
        thread.start();
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==reset){
			message.setText("");
		}
		else if(event.getSource()==send){
			try {
				//socket = new Socket("localhost",2222);
				s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				//s1=socket.getInputStream();
				//DataInputStream br=new DataInputStream(s1);
				String str1,str2;
				str1=message.getText();
				bw.writeUTF("3"+"-"+name+"-"+str1);
				/*str2=new String(br.readUTF());
				if(str2!=null){
					contentArea.append(str2+"\n");
				}*/
				bw.flush();
				message.setText("");
				//br.close();
				//bw.close();
				//s1.close();
				//s2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(event.getSource()==pchat){
			new copchat(socket,name);
			//myframe.dispose();
		}
		else if(event.getSource()==out){
			try {
				
				s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				bw.writeUTF("4"+"-"+name);
				new Login(socket);
				myframe.dispose();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	public  void xianshi(String mess){
		if(mess!=null){
			contentArea.append(mess+"\n");
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
     room roo=new room(socket,name);
     
		}
}


class copchat extends JFrame implements ActionListener{
	private static Socket socket;
	static String name1;
	JFrame myframe=new JFrame("copchat");
	private JButton but1=new JButton("重置");
	private JButton but2=new JButton("确定");
	private JTextField nam=new JTextField();
	public copchat(Socket socket,String name){
		this.socket=socket;
		this.name1=name;
		//JFrame myframe=new JFrame("Login");
		//JFrame.setDefaultLookAndFeelDecorated(true);
		Container contentPane = myframe.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.lightGray);
		myframe.setSize(500,380);
		JLabel label1=new JLabel("私聊连接界面");
		label1.setBounds(220,10,200,50);
		 JLabel label2=new JLabel("用户名");
		 label2.setBounds(90,85,50,60);
		    nam.setBounds(150,150,200,30);
		    but1.setBounds(90,220,80,30);
		    but2.setBounds(270,220,80,30);
		    contentPane.add(label1);
			contentPane.add(label2);
			contentPane.add(but1);
			contentPane.add(but2);
			contentPane.add(nam);
			but1.addActionListener(this);
			but2.addActionListener(this);
			myframe.setVisible(true);
			myframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
	}
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==but1){
			nam.setText("");
		}
		else if(event.getSource()==but2){
			try {
				//socket = new Socket("localhost",2222);
				OutputStream s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				InputStream s1=socket.getInputStream();
				DataInputStream br=new DataInputStream(s1);
				String str1;
				str1=nam.getText();//得到用户名
				bw.writeUTF("5"+"-"+name1+"-"+str1);//传私聊用户名
				/*int R=br.readInt();//服务器传过来信息，可以私聊（1）就私聊，不可以（0）就弹窗
				System.out.println(R);*/
				while(room.tag == 0)
				{
					
				}
				if(room.H==0){//没登录进行弹窗
					String m3=new String(br.readUTF());
					JDialog dia=new JDialog();
					dia.setSize(200, 100);
					dia.setVisible(true);
					JOptionPane.showMessageDialog(dia,m3);
				}else if(room.H ==1){//登录显示聊天界面
					new privateroom(socket,name1,str1);
					privateroom.clear();
					myframe.dispose();
				}
				bw.flush();
				//br.close();
				//bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	
}



class privateroom extends JFrame implements ActionListener{
	   static Socket socket;
 static String name1;
 static String name2;
	OutputStream s2;
	 InputStream s1;
	 String[]str=new String[999];
	 int i=0;
	JFrame myframe=new JFrame("privateroom");
	JButton reset = new JButton("清空");  
 JButton send= new JButton("发送");  
 JTextArea message = new JTextArea();
 static JTextArea contentArea = new JTextArea();  
	public privateroom(Socket socket,String name1,String name2){
		this.name1=name1;
		this.name2=name2;
		this.socket=socket;
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel();  
	    JPanel southPanel=new JPanel(new BorderLayout());   
	    //JPanel centerPanel=new JPanel(new BorderLayout());  
	    
     contentArea.setEditable(false);  
     contentArea.setForeground(Color.blue);
     //contentArea.setPreferredSize(new Dimension(,30));
     //centerPanel.add(contentArea,"Center");
       
     message.setLineWrap(true);
     message.setWrapStyleWord(true);
     JPanel mesPanel=new JPanel(new BorderLayout());
     mesPanel.add(new JScrollPane(message),"Center");
     
     
     southPanel.setBorder(new TitledBorder("Send Message")); 
     JPanel southeastPanel=new JPanel(new GridLayout(2,1));
     southPanel.add(mesPanel, "Center");
     southPanel.add(southeastPanel, "East");
     southeastPanel.add(send);
     southeastPanel.add(reset);
     southPanel.setPreferredSize(new Dimension(600,120)); 
     
     JScrollPane rightPanel = new JScrollPane(contentArea);  
     rightPanel.setBorder(new TitledBorder("Message Box"));
     
     JLabel label1=new JLabel("Private Conversation: "+name1+" "+"To"+" "+name2,SwingConstants.CENTER);
     northPanel.add(label1);
     reset.addActionListener(this);
		send.addActionListener(this);
     myframe.setLayout(new BorderLayout());  
     myframe.add(northPanel, "North");  
     myframe.add(rightPanel, "Center");  
     myframe.add(southPanel, "South");  
     myframe.setSize(600, 400);  
     myframe.setVisible(true); 
     myframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==reset){
			message.setText("");
		}
		else if(event.getSource()==send){
			try {
				//socket = new Socket("localhost",2222);
				s2=socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				//s1=socket.getInputStream();
				//DataInputStream br=new DataInputStream(s1);
				String str1;
				str1=message.getText();
				bw.writeUTF("6"+"-"+name1+"-"+name2+"-"+str1);
				/*str2=new String(br.readUTF());
				if(str2!=null){
					contentArea.append(str2+"\n");
				}*/
				bw.flush();
				message.setText("");
				//br.close();
				//bw.close();
				//s1.close();
				//s2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void xianshi(String mess){
		if(mess!=null){
			contentArea.append(mess+"\n");
		}
	}
	public static void clear(){
		contentArea.setText("");
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
  privateroom roo=new privateroom(socket,name1,name2);
  
		}
}

