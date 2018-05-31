package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frame2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myframe=new JFrame("Login");
		JFrame.setDefaultLookAndFeelDecorated(true);
		Container contentPane = myframe.getContentPane();
		contentPane.setLayout(null);
		//contentPane.setBackground(Color.CYAN);
		myframe.setSize(500,380);
		JLabel label1=new JLabel("登录界面");
		label1.setBounds(220,10,200,50);
		 JLabel label2=new JLabel("用户名");
		 label2.setBounds(90,85,50,60);
		    JLabel label3=new JLabel("密	     钥");
		    label3.setBounds(90,145,50,60);
		    JTextField nam=new JTextField();
		    nam.setBounds(150,100,200,30);
		    JTextField pas=new JTextField();
		    pas.setBounds(150,160,200,30);
		    JButton but1=new JButton("重置");
		    but1.setBounds(90,220,70,30);
		    JButton but2=new JButton("验证细节");
		    but2.setBounds(170,220,100,30);
		    JButton but3=new JButton("登录");
		    but3.setBounds(280,220,70,30);
		    contentPane.add(label1);
			contentPane.add(label2);
			contentPane.add(label3);
			contentPane.add(but1);
			contentPane.add(but2);
			contentPane.add(but3);
			contentPane.add(nam);
			contentPane.add(pas);
			myframe.setVisible(true);
			myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	    
	}

}
