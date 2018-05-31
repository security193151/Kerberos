package temp;
/*
1-�����û�
2-���ضԷ���Կ 
3-���Ͷ���-ԭ����-�Գ�Կ
4-���Ͷ���-ȷ����Ϣ
5-���Ͷ���-ԭ����-�Ի�*/
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
	private String myname;//���û����û���
	private String hisname=null;//�Է����û���
	private String Sk;//�Գ�Կ
	private String hisPk;//�Գ�Կ
	private Socket socket;
	private JButton reset = new JButton("���");  
	private JButton send= new JButton("����");  
	private JButton detail= new JButton("����ϸ��");
	private JButton out= new JButton("�˳���¼");
	private JButton choose= new JButton("ѡ������");
	JTextArea message = new JTextArea();  
	JTextArea contentArea = new JTextArea();  
	JTextArea onlinelist=new JTextArea();
	int tag=0;//ȷ����Ϣ����tagΪ1��ʾ����ͨ��
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
							JOptionPane.showMessageDialog(null,hisname+"������������ͨ����");
							OutputStream s2= socket.getOutputStream();
							DataOutputStream bw=new  DataOutputStream(s2);
							bw.writeUTF("4-"+hisname+"-����һ��ȷ����Ϣ");
							bw.flush();
							break;
						case 4:
							tag=1;
							JOptionPane.showMessageDialog(null,"�Է���ͳһ���ĶԻ�����");
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
					JOptionPane.showMessageDialog(null,"����ѡ��һ���������");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(event.getSource()==detail){

		}
		else if(event.getSource()==choose){
			String s=JOptionPane.showInputDialog("���������������û���"); 
			hisname=s;
			OutputStream s2;
			try {
				s2 = socket.getOutputStream();
				DataOutputStream bw=new  DataOutputStream(s2);
				bw.writeUTF("2-"+hisname+"-�����乫Կ");
				bw.writeUTF("3-"+hisname+"-"+myname+"-����һ�ѶԳ�Կ");
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
