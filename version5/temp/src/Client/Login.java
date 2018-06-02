package Client;

import Clock.Clock;
import DES.DesOperation;
import RSA.RSA;
import fileOperation.fileOperation;
import generateKey.generateKey;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Login extends JFrame implements ActionListener,MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Socket socket1,socket2,socket3;
	private JFrame myframe=new JFrame("Login");
	private JButton button1=new JButton("重置");
	private JButton button2=new JButton("登录");
	private JTextField Name=new JTextField(22);
	private JPasswordField KC=new JPasswordField(22);
	private JTextArea process=new JTextArea();
	private JPanel southPanel2=new JPanel(new GridLayout(1,6));
	public Login(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel(); 
		northPanel.setLayout(new GridLayout(4,1,5,5));
		northPanel.setPreferredSize(new Dimension(600,250));

		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();

		JPanel southPanel=new JPanel(new BorderLayout()); 
		southPanel.setBorder(new TitledBorder("验证过程"));  
		
		southPanel2.setPreferredSize(new Dimension(600,20));
		//southPanel.setPreferredSize(new Dimension(600,120));
        southPanel2.setVisible(false);

		JLabel label=new JLabel("登录界面");
		JLabel label1=new JLabel("用户名");
		JLabel label2=new JLabel("密    码");
		JLabel lable3=new JLabel("登录成功，确定登录>>>>>>>");
		lable3.addMouseListener(this);
		//lable3.setVisible(false);

		//KC.setPreferredSize(new Dimension(250,20));

		process.setLineWrap(true);
		process.setWrapStyleWord(true);
		process.setEditable(false);
		southPanel2.add(lable3);
		southPanel.add(new JScrollPane(process),"Center");
		southPanel.add(southPanel2,"South");

		panel1.add(label1);
		panel1.add(Name);
		panel2.add(label2);
		panel2.add(KC);
		panel3.add(button1);
		panel3.add(button2);
		panel4.add(label);


		northPanel.add(panel4);
		northPanel.add(panel1);
		northPanel.add(panel2);
		northPanel.add(panel3);

		button1.addActionListener(this);
		button2.addActionListener(this);

		myframe.setLayout(new BorderLayout());  
		myframe.add(northPanel, "North");  
		myframe.add(southPanel, "Center");  
		myframe.setSize(600,400);  
		myframe.setVisible(true); 
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==button1){
			Name.setText("");
			KC.setText("");
			process.setText("");
		}
		else if(event.getSource()==button2){
			try {

				String regEx_name="^C[0-9][0-9][0-9]$";
			//	Pattern pattern_name = Pattern.compile(regEx_name, Pattern.CASE_INSENSITIVE);//不区分大小写
				Pattern pattern_name=Pattern.compile(regEx_name);
				Matcher matcher_name=pattern_name.matcher(Name.getText());
				String regEx_kc="^[0-9a-zA-Z]{7}$";
				Pattern pattern_kc=Pattern.compile(regEx_kc);
				Matcher matcher_kc=pattern_kc.matcher(KC.getText());

				if(matcher_name.matches()==false||matcher_kc.matches()==false)
				{
					JOptionPane.showMessageDialog(null, "请输入正确的用户名与密码！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					socket1 = new Socket("localhost",1110);//与AS连接
					OutputStream out1 = socket1.getOutputStream();
					DataOutputStream bw1 = new DataOutputStream(out1);
					InputStream In1 = socket1.getInputStream();
					DataInputStream br1 = new DataInputStream(In1);
					String pwd=KC.getText();

					String IDc=Name.getText();
					String IDtgs="S002";
					String TS1=Clock.getTime();

					StringBuilder writeAS_builder=new StringBuilder("000001");
					process.setText("控制字段:000001\n");
					writeAS_builder.append(IDc);
					process.append("IDc:"+IDc+"  ");
					writeAS_builder.append(IDtgs);
					process.append("IDtgs:"+IDtgs+"  ");
					writeAS_builder.append(TS1);
					process.append("TS1:"+TS1+"\n");
					String writeAS=writeAS_builder.toString();
					//System.out.println(writeAS);

					bw1.writeUTF(writeAS);
					process.append("C->AS:"+writeAS+"\n\n");
					String readAS=br1.readUTF();
					process.append("AS->C:"+readAS+"\n");
					socket1.close();

					if(readAS.substring(0,6).equals("111111"))
					{
						process.append("控制字段:111111"+"\n");
						JOptionPane.showMessageDialog(null, "不存在此用户！", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}

					String cipher=readAS.substring(6);
					String data=DesOperation.DesDecryption(cipher, pwd);
					String ticket_tgs=data.substring(25);
					String Kc_tgs=data.substring(0,7);
					process.append("控制字段:"+readAS.substring(0,6)+"\n");
					process.append("报文解密结果为:"+data+"\n");
					if(!data.substring(7,11).equals("S002"))
					{

						JOptionPane.showMessageDialog(null, "密码错误，验证失败！", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					process.append("Kc,tgs:"+Kc_tgs+", IDtgs:"+data.substring(7,11)+", TS2:"+data.substring(11,23)+", Lifetime:"+data.substring(23,25)+"\n"+"ticket_tgs:"+ticket_tgs+"\n\n");


					socket2 = new Socket("localhost",2222);//与TGS连接
					OutputStream out2 = socket2.getOutputStream();
					DataOutputStream bw2 = new DataOutputStream(out2);
					InputStream In2 = socket2.getInputStream();
					DataInputStream br2 = new DataInputStream(In2);

					String IDv="S003";
					String ADc=InetAddress.getLocalHost().getHostAddress().toString();
					String TS3=Clock.getTime();
					StringBuilder Authenticator_builder=new StringBuilder(IDc);
					Authenticator_builder.append(ADc);
					Authenticator_builder.append(TS3);
					String pre_Authenticator=Authenticator_builder.toString();
					String Authenticator=DesOperation.DesEncryption(pre_Authenticator,Kc_tgs);
					StringBuilder writeTGS_builder=new StringBuilder("000011");
					writeTGS_builder.append(IDv);
					writeTGS_builder.append(ticket_tgs);
					writeTGS_builder.append(Authenticator);
					String writeTGS=writeTGS_builder.toString();
					bw2.writeUTF(writeTGS);
					process.append("c->TGS:"+writeTGS+"\n");
					process.append("控制字段:000011, IDv:"+IDv+", ticket_tgs:"+ticket_tgs+", Authenticator:"+Authenticator+"\n\n");

					String readTGS=br2.readUTF();
					process.append("TGS->c:"+readTGS+"\n");
					process.append("控制字段:"+readTGS.substring(0,6)+"\n");
					String dataTGS=DesOperation.DesDecryption(readTGS.substring(6), Kc_tgs);
					String Kc_v=dataTGS.substring(0,7);
					String TS4=dataTGS.substring(11,23);
					String ticket_v=dataTGS.substring(23);
					process.append("报文解密结果为:"+dataTGS+"\n"+"Kc_v:"+Kc_v+", IDv:"+IDv+", TS4:"+TS4+", ticket_v:"+ticket_v+"\n\n");
					socket2.close();


					socket3 = new Socket("localhost",3333);//与Server连接
					OutputStream out3 = socket3.getOutputStream();
					DataOutputStream bw3 = new DataOutputStream(out3);
					InputStream In3 = socket3.getInputStream();
					DataInputStream br3 = new DataInputStream(In3);
					
					String TS5=Clock.getTime();
					StringBuilder Authenticator_c_builder=new StringBuilder(IDc);
					Authenticator_c_builder.append(ADc);
					Authenticator_c_builder.append(TS5);
					String Authenticator_c=DesOperation.DesEncryption(Authenticator_c_builder.toString(), Kc_v);
					StringBuilder writeAPP_builder=new StringBuilder("000101");
					writeAPP_builder.append(ticket_v);
					writeAPP_builder.append(Authenticator_c);
					String writeAPP=writeAPP_builder.toString();
					
					bw3.writeUTF(writeAPP);
					process.append("c->V:"+writeAPP+"\n"+"控制字段:000101, ticket_v:"+ticket_v+", Authenticator_c:"+Authenticator_c+"\n\n");
					
					String readAPP=br3.readUTF();
					process.append("V->c:"+readAPP+"\n");
				//	System.out.println("TS5:"+TS5);
					if(readAPP.substring(0,6).equals("111110"))
					{
						JOptionPane.showMessageDialog(null, "该用户已登录，请勿重复登录！", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
					String dataAPP=readAPP.substring(6);
					
					String pre_dataAPP=DesOperation.DesDecryption(dataAPP, Kc_v);
					
					process.append("控制字段:"+readAPP.substring(0, 6)+"\n");
					process.append("报文解密结果为(TS5+1):"+pre_dataAPP+"\n\n");
					southPanel2.setVisible(true);//将下面的面板设为可见
					}
					
				}
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
			
			try {
				myframe.dispose();
				new Chat(socket3,Name.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
