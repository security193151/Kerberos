package temp;
/*
1-在线用户
2-返回对方公钥 
3-发送对象-原对象-对称钥
4-发送对象-确认消息
5-发送对象-原对象-对话*/
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Vector;

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
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


public class frame1 extends JFrame implements ActionListener{

	JFrame myframe=new JFrame("chat");
	private String myname;//本用户的用户名
	private String hisname=null;//对方的用户名
	private String Sk;//对称钥
	private String hisPk;//对称钥
	private Socket socket;
	private JButton reset = new JButton("清空");  
	private JButton send= new JButton("发送");  
	private JButton detail= new JButton("传送细节");
	private JButton out= new JButton("退出登录");
	private JButton choose= new JButton("选择聊天");
	JTextArea message = new JTextArea();  
	JTextArea contentArea = new JTextArea();  
	JTextArea onlinelist=new JTextArea();
	int tag=0;//确认信息，当tag为1表示可以通信
	public frame1(Socket sock,String na)
	{
		socket=sock;
		myname=na;
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel();  
		JPanel southPanel=new JPanel(new BorderLayout());  
		JScrollPane leftPanel=new JScrollPane();  


		contentArea.setEditable(false);  
		contentArea.setForeground(Color.blue);


		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		JPanel mesPanel=new JPanel(new BorderLayout());
		mesPanel.add(new JScrollPane(message),"Center");


		southPanel.setBorder(new TitledBorder("Send Message")); 
		JPanel southeastPanel=new JPanel(new GridLayout(5,1));
		southPanel.add(mesPanel, "Center");
		southPanel.add(southeastPanel, "East");
		southeastPanel.add(send);
		southeastPanel.add(reset);
		southeastPanel.add(detail);
		southeastPanel.add(choose);
		southeastPanel.add(out);

		southPanel.setPreferredSize(new Dimension(600,130));

		onlinelist.setEditable(false);
		leftPanel = new JScrollPane(onlinelist);  
		leftPanel.setBorder(new TitledBorder("Online Person"));

		JScrollPane rightPanel = new JScrollPane(contentArea);  
		rightPanel.setBorder(new TitledBorder("Message Box"));

		JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel);  
		centerSplit.setDividerLocation(100);  

		JLabel label1=new JLabel("Welcome",SwingConstants.CENTER);
		northPanel.add(label1);

		reset.addActionListener(this);
		send.addActionListener(this);
		detail.addActionListener(this);
		choose.addActionListener(this);
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

						InputStream s1=socket.getInputStream();
						DataInputStream br=new DataInputStream(s1);
						String mess=br.readUTF();
						String[]string = mess.split("-");
						int NO=Integer.parseInt(string[0]);
						switch(NO){
						case 1:
							onlinelist.setText(string[1]);
							break;
						case 2:
							hisPk=string[1];
							break;
						case 3:
							hisname=string[2];
							Sk=string[3];
							tag=1;
							JOptionPane.showMessageDialog(null,hisname+"请求与您进行通话！");
							OutputStream s2= socket.getOutputStream();
							DataOutputStream bw=new  DataOutputStream(s2);
							bw.writeUTF("4-"+hisname+"-这是一条确认消息");
							bw.flush();
							break;
						case 4:
							tag=1;
							JOptionPane.showMessageDialog(null,"对方已统一您的对话请求！");
							break;
						case 5:
							contentArea.append(string[1]);
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



	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==reset){
			message.setText("");
		}
		else if(event.getSource()==send){
			try {
				if(hisname!=null&&tag==1){
					String Mess=message.getText();
					OutputStream s2= socket.getOutputStream();
					DataOutputStream bw=new  DataOutputStream(s2);
					bw.writeUTF("5-"+hisname+"-"+myname+"-"+Mess);
					message.setText("");
					bw.flush();
				}
				else{
					JOptionPane.showMessageDialog(null,"请先选择一个聊天对象！");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(event.getSource()==detail){

		}
		else if(event.getSource()==choose){
			String s=JOptionPane.showInputDialog("请输入聊天对象的用户名"); 
			hisname=s;
			OutputStream s2;
			try {
				s2 = socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				bw.writeUTF("2-"+hisname+"-请求其公钥");
				bw.writeUTF("3-"+hisname+"-"+myname+"-这是一把对称钥");
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(event.getSource()==out){
			OutputStream s2;
			try {
				s2 = socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				bw.writeUTF("7-"+myname);
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
