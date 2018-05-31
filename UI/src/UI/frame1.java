package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

//登录界面
public class frame1 extends JFrame{
	private JFrame myframe=new JFrame("Login");
	private JButton button1=new JButton("重置");
	private JButton button2=new JButton("登录");
	private JTextField name=new JTextField(22);
	private JTextArea Key=new JTextArea();
	private JTextArea process=new JTextArea();
	private Image image;
	private static final long serialVersionUID=-1588458291133087637L;
	public frame1(){
		//JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel contentPane=new JPanel(){  
            @Override  
            public void paint(Graphics g) {  
                ImageIcon icon=new ImageIcon("2.jpg");  
         image=icon.getImage();  
         g.drawImage(image, 0,0,null);  
                // TODO Auto-generated method stub  
                  
            }  
        };    
		
		JPanel northPanel=new JPanel(); 
		northPanel.setLayout(new GridLayout(4,1,5,5));
		northPanel.setPreferredSize(new Dimension(600,250));
		
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();
		
		JPanel southPanel=new JPanel(new BorderLayout()); 
		southPanel.setBorder(new TitledBorder("验证过程"));  
		//southPanel.setPreferredSize(new Dimension(600,120));
		
		
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
	     myframe.add(contentPane);
		 /*myframe.setLayout(new BorderLayout());  
	     myframe.add(northPanel, "North");  
	     myframe.add(southPanel, "Center");  */
	     myframe.setSize(600,400);  
	     myframe.setVisible(true); 
	     myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String []args){
		frame1 f=new frame1();
	}
}
