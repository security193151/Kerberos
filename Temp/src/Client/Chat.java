package Client;

import DES.DesOperation;
import RSA.RSA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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

public class Chat {
	private Socket socket;
	private String user;
	private int tag;//�Ƿ���ѡ��ĳ���û�
	private String target_user;
	Map<String, String>PKList = new HashMap<String, String>();//�û�������Ӧ��n
	private String An;
	private String SK;
	Map<String, String>sessionKeyList = new HashMap<String, String>();//�û�������Ӧ�ĻỰ��Կ
	ArrayList<String> online = new ArrayList<String>();//�����û�
	private JFrame myframe=new JFrame("chat");
	private JButton reset = new JButton("���");  
	private JButton send= new JButton("����");  
	private JButton out= new JButton("�˳���¼");
	private JLabel user1=new JLabel();
	private JLabel user2=new JLabel();
	private JLabel user3=new JLabel();
	private JLabel user4=new JLabel();
	ArrayList<JLabel> onlinelb=new ArrayList<JLabel>();
	int onlineNumber;//���Լ�����������û�
	private JTextArea message = new JTextArea();  //����
	private JTextArea contentArea = new JTextArea();  //��Ϣ��ʾ
	//private JTextArea onlinelist=new JTextArea();//�����û�
	private JTextArea MessageDetail=new JTextArea();//��Ϣϸ��
	public Chat(Socket sock,String id) throws IOException{
		socket=sock;
		user=id;
		target_user=null;
		tag=0;
		An="";///////////////////////////////////////////////////////////////////???????????????
		SK="";//////////////////////////////////////////////////////???????????????????

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


		southPanel.setBorder(new TitledBorder("���Ϳ�")); 
		JPanel southeastPanel=new JPanel(new GridLayout(3,1));
		southPanel.add(mesPanel, "Center");
		southPanel.add(southeastPanel, "East");
		southeastPanel.add(send);
		southeastPanel.add(reset);
		southeastPanel.add(out);
		southPanel.setPreferredSize(new Dimension(600,130));

		/*onlinelist.setEditable(false);
		onlinelist.setLineWrap(true);
		onlinelist.setWrapStyleWord(true);
		leftPanel = new JScrollPane(onlinelist);  */
		leftPanel.setBorder(new TitledBorder("�����б�"));

		MessageDetail.setEditable(false);
		MessageDetail.setLineWrap(true);
		MessageDetail.setWrapStyleWord(true);
		EastPanel.add(new JScrollPane(MessageDetail),"Center");
		EastPanel.setBorder(new TitledBorder("��Ϣϸ��"));
		EastPanel.setPreferredSize(new Dimension(200,270));

		JScrollPane rightPanel = new JScrollPane(contentArea);  
		rightPanel.setBorder(new TitledBorder("��Ϣ��"));

		JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel);  
		centerSplit.setDividerLocation(130);  

		JLabel label1=new JLabel("Welcome",SwingConstants.CENTER);
		northPanel.add(label1);

		myframe.setLayout(new BorderLayout());  
		myframe.add(northPanel, "North");  
		myframe.add(centerSplit, "Center");  
		myframe.add(southPanel, "South");  
		myframe.add(EastPanel, "East");  
		myframe.setSize(800, 500);  
		myframe.setVisible(true); 
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
							case "001000"://�Գ�Կ
								String data1=readServer.substring(14);
								String target_id1=readServer.substring(10,14);
								String Bn=PKList.get(target_id1);
								String session_key1=RSA.RsaDecryption_Ver(data1, new BigInteger(An),new BigInteger(Bn),new BigInteger(SK) );
								if(session_key1==null)
								{
								}
								else
									sessionKeyList.put(target_id1, session_key1);

								break;
							case "001001"://������Ϣ
								String data2=readServer.substring(14);
								String target_id2=readServer.substring(10,14);
								String session_key2=sessionKeyList.get(target_id2);
								String msg=DesOperation.DesDecryption(data2, session_key2);
								/*
								 * �ļ���������������ʾ(��label����)
								 */
								break;
							case "001010"://�û�����������
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
					OutputStream s2= socket.getOutputStream();
					DataOutputStream bw=new  DataOutputStream(s2);
					StringBuilder msg_builder=new StringBuilder("001001");
					String session_key=sessionKeyList.get(target_user);
					msg_builder.append(user);
					msg_builder.append(target_user);
					msg_builder.append(DesOperation.DesEncryption(msg, session_key));
					bw.writeUTF(msg_builder.toString());
				}
				else{
					JOptionPane.showMessageDialog(null, "����ѡ��������󣡣���");
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}


/*
 {
 tag=1;
 target_user=
 }
 */




