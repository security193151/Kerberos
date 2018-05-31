package Client;

import DES.DesOperation;
import RSA.RSA;
import generateKey.generateKey;
import fileOperation.fileOperation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import Clock.Clock;

public class Chat extends JFrame implements ActionListener,MouseListener{
	private Socket socket;
	private String user;
	private int tag;//是否已选中某个用户
	private String target_user;
	Map<String, String>PKList = new HashMap<String, String>();//用户名，对应的n
	private String An;
	private String SK;
	Map<String, String>sessionKeyList = new HashMap<String, String>();//用户名，对应的会话密钥
	ArrayList<String> online = new ArrayList<String>();//在线用户
	private JFrame myframe=new JFrame("chat");
	private JButton reset = new JButton("清空");  
	private JButton send= new JButton("发送");  
	private JButton out= new JButton("退出登录");
	private JLabel user1=new JLabel();
	private JLabel user2=new JLabel();
	private JLabel user3=new JLabel();
	private JLabel user4=new JLabel();
	private JLabel prelable=new JLabel();//用于鼠标事件中，指示前一个被点击的标签
	ArrayList<JLabel> onlinelb=new ArrayList<JLabel>();
	int onlineNumber;//除自己以外的其他用户
	private JTextArea message = new JTextArea();  //发送
	private JTextArea contentArea = new JTextArea();  //消息显示
	//private JTextArea onlinelist=new JTextArea();//在线用户
	private JTextArea MessageDetail=new JTextArea();//消息细节
	public Chat(Socket sock,String id) throws IOException{
		socket=sock;
		user=id;
		target_user=null;
		tag=0;
		An="891695298380615001506225469695029722875464135973";///////////////////////////////////////////////////////////////////???????????????
		SK="127760179010238107697078585939145709214191612033";//////////////////////////////////////////////////////???????????????????

		PKList.put("C001", "1271035385756482724459497435172204207466105945727");
		PKList.put("C002", "891695298380615001506225469695029722875464135973");
		PKList.put("C003","677850161218135790798839158975551746813200513391");
		PKList.put("C004", "986670791148131547660368448216656227083536257253");

		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel();  
		JPanel southPanel=new JPanel(new BorderLayout());  
		JPanel leftPanel=new JPanel(new GridLayout(10,1));  
		JPanel EastPanel=new JPanel(new BorderLayout());


		contentArea.setEditable(false);  
		contentArea.setForeground(Color.blue);
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);

		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		JPanel mesPanel=new JPanel(new BorderLayout());
		mesPanel.add(new JScrollPane(message),"Center");


		southPanel.setBorder(new TitledBorder("发送框")); 
		JPanel southeastPanel=new JPanel(new GridLayout(3,1));
		southPanel.add(mesPanel, "Center");
		southPanel.add(southeastPanel, "East");
		southeastPanel.add(send);
		southeastPanel.add(reset);
		southeastPanel.add(out);
		southPanel.setPreferredSize(new Dimension(600,130));

		
		leftPanel.setBorder(new TitledBorder("在线列表"));
		leftPanel.setBackground(Color.WHITE);

		MessageDetail.setEditable(false);
		MessageDetail.setLineWrap(true);
		MessageDetail.setWrapStyleWord(true);
		EastPanel.add(new JScrollPane(MessageDetail),"Center");
		EastPanel.setBorder(new TitledBorder("消息细节"));
		EastPanel.setPreferredSize(new Dimension(200,270));
		EastPanel.setBackground(Color.WHITE);

		JScrollPane rightPanel = new JScrollPane(contentArea);  
		rightPanel.setBorder(new TitledBorder("消息框"));
		rightPanel.setBackground(Color.WHITE);

		JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel);  
		centerSplit.setDividerLocation(130);  

		JLabel label1=new JLabel("Welcome, "+user,SwingConstants.CENTER);
		northPanel.add(label1);
		
		reset.addActionListener(this);
		send.addActionListener(this);
		out.addActionListener(this);
		
		myframe.setLayout(new BorderLayout());  
		myframe.add(northPanel, "North");  
		myframe.add(centerSplit, "Center");  
		myframe.add(southPanel, "South");  
		myframe.add(EastPanel, "East");  
		myframe.setSize(800, 500);  
		myframe.setVisible(true); 
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myframe.addWindowListener(new WindowAdapter(){//添加一个窗口监听
            public void windowClosing(WindowEvent e) {//窗口关闭事件
            	
					try {
						OutputStream	s2 = socket.getOutputStream();
						DataOutputStream bw=new  DataOutputStream(s2);
	    				StringBuilder offline_builder=new StringBuilder("0010100");
	    				offline_builder.append(user);
	    				bw.writeUTF(offline_builder.toString());
	    				myframe.dispose();
	    				Thread.interrupted();
	    				System.exit(0);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
    				
    			
            }
        });

		user1.setOpaque(true);
        user2.setOpaque(true);
        user3.setOpaque(true);
        user4.setOpaque(true);
        user1.setHorizontalAlignment(SwingConstants.CENTER);
        user2.setHorizontalAlignment(SwingConstants.CENTER);
        user3.setHorizontalAlignment(SwingConstants.CENTER);
        user4.setHorizontalAlignment(SwingConstants.CENTER);
        user1.setBackground(Color.WHITE);
        user2.setBackground(Color.WHITE);
        user3.setBackground(Color.WHITE);
        user4.setBackground(Color.WHITE);
		user1.addMouseListener(this);
		user2.addMouseListener(this);
		user3.addMouseListener(this);
		user4.addMouseListener(this);
		onlinelb.add(user1);
		onlinelb.add(user2);
		onlinelb.add(user3);
		onlinelb.add(user4);
		Thread thread = new Thread()
		{
			public void run()
			{
				while(true){
					try {

						InputStream sr2= socket.getInputStream();
						DataInputStream br = new DataInputStream(sr2);

						String readOnlineUser=br.readUTF();
						onlineNumber=(readOnlineUser.length()-6)/4;



						for(int i=0;i<onlineNumber;i++)
						{
							onlinelb.get(i).setText(readOnlineUser.substring(6+i*4,6+i*4+4));
							leftPanel.add(onlinelb.get(i));
							online.add(readOnlineUser.substring(6+i*4,6+i*4+4));
						}
						myframe.revalidate();
						myframe.repaint();

						while(true)
						{
							String readServer=br.readUTF();
							String control =readServer.substring(0, 6);
							switch (control)
							{
							case "001000"://对称钥
								String data1=readServer.substring(14);
								String target_id1=readServer.substring(6,10);
								String Bn=PKList.get(target_id1);
								System.out.println("发送方:"+target_id1);
								System.out.println("Bn:"+Bn);
								System.out.println("需要解密的内容为:"+data1);
								
								String session_key1=RSA.RsaDecryption_Ver(data1, new BigInteger(An),new BigInteger(Bn),new BigInteger(SK) );
								if(session_key1==null)
								{
								}
								else
									sessionKeyList.put(target_id1, session_key1);

								break;
							case "001001"://聊天信息
								String data2=readServer.substring(14);
								String target_id2=readServer.substring(6,10);
								String session_key2=sessionKeyList.get(target_id2);
								String msg=DesOperation.DesDecryption(data2, session_key2);
								if(target_id2.equals(target_user))//当前窗口显示的是选定的聊天对象
								{
									StringBuilder msg_builder=new StringBuilder(target_id2);
									msg_builder.append(":  ");
									msg_builder.append(Clock.getTime());
									msg_builder.append("\r\n");
									msg_builder.append(msg+"\r\n");
									fileOperation.writeFile(msg_builder.toString(), target_id2+".txt");
									contentArea.append(target_id2+":  "+Clock.getTime()+"\n");
									contentArea.append(msg+"\n");
									
								}
								else//存在聊天记录文件中，label变色提醒
								{
									StringBuilder msg_builder=new StringBuilder(target_id2);
									msg_builder.append(":  ");
									msg_builder.append(Clock.getTime());
									msg_builder.append("\r\n");
									msg_builder.append(msg+"\r\n");
									fileOperation.writeFile(msg_builder.toString(), target_id2+".txt");
									for(int i=0;i<4;i++)
									{
										if(onlinelb.get(i).getText().equals(target_id2))
											onlinelb.get(i).setBackground(Color.YELLOW);
									}
								}
								
							
								break;
							case "001010"://用户上下线提醒
								String target_id3=readServer.substring(7,11);
								if(readServer.substring(6,7).equals("0"))
								{
									for(int i=0;i<4;i++)
									{
										if(onlinelb.get(i).getText().equals(target_id3))
											leftPanel.remove(onlinelb.get(i));
									}
									onlineNumber--;
									sessionKeyList.remove(target_id3);
									online.remove(target_id3);
									if(target_id3.equals(target_user))
									{
										target_user=null;
										tag=0;
									}
								}
								else
								{
									leftPanel.removeAll();
									online.add(target_id3);
									int i=0;
									for(String onlineID:online)
									{
										onlinelb.get(i).setText(onlineID);
										leftPanel.add(onlinelb.get(i));
										i++;
									}
									onlineNumber++;		
								}
								myframe.revalidate();
								myframe.repaint();
								break;
							}
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



	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==reset){
			message.setText("");
		}


		else if(event.getSource()==send){
			try {
				if(tag==1){
					String msg=message.getText();
					StringBuilder msgfile_builder=new StringBuilder(user);
					msgfile_builder.append(":  ");
					msgfile_builder.append(Clock.getTime());
					msgfile_builder.append("\r\n");
					msgfile_builder.append(msg+"\r\n");
					fileOperation.writeFile(msgfile_builder.toString(), target_user+".txt");
					contentArea.append(user+":  "+Clock.getTime()+"\n");
					contentArea.append(msg+"\n");
					OutputStream s2= socket.getOutputStream();
					DataOutputStream bw=new  DataOutputStream(s2);
					StringBuilder msg_builder=new StringBuilder("001001");
					String session_key=sessionKeyList.get(target_user);
					msg_builder.append(user);
					msg_builder.append(target_user);
					
					msg_builder.append(DesOperation.DesEncryption(msg, session_key));
					
					bw.writeUTF(msg_builder.toString());
					
					System.out.println("发送对称钥:"+msg_builder.toString());
					
					message.setText("");
				}
				else{
					JOptionPane.showMessageDialog(null, "请先选择聊天对象！！！");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if(event.getSource()==out){
			try {
				OutputStream 	s2 = socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				StringBuilder offline_builder=new StringBuilder("0010100");
				offline_builder.append(user);
				bw.writeUTF(offline_builder.toString());
				myframe.dispose();
				System.exit(0);
				//socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e){
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			contentArea.setText("");
			prelable.setBackground(Color.WHITE);
			((JLabel)e.getComponent()).setBackground(Color.LIGHT_GRAY);
			prelable=(JLabel)e.getComponent();	
			tag=1;
			 target_user=((JLabel)e.getComponent()).getText();
			 try {
				contentArea.append(fileOperation.readFile(target_user+".txt"));
			} catch (IOException e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			}
			 if(sessionKeyList.containsValue(target_user))
			 {
				 
			 }
			 else
			 {
				
					try {
						// MessageDetail.append("目标用户"+target_user);
						 String session_key=generateKey.getKey(7);
						 StringBuilder sessionKeyBuilder=new StringBuilder("001000");
						 sessionKeyBuilder.append(user);
						 sessionKeyBuilder.append(target_user);
						 sessionKeyBuilder.append(RSA.RsaEncryption_Sig(session_key, new BigInteger(An), new BigInteger(PKList.get(target_user)), new BigInteger(SK)));
						 OutputStream s2= socket.getOutputStream();
						 DataOutputStream bw=new  DataOutputStream(s2);
						 bw.writeUTF(sessionKeyBuilder.toString());
						 sessionKeyList.put(target_user, session_key);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			 }

		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}






