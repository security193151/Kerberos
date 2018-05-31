package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class frame1{

	public static void main(String[] args) {
		JFrame myframe=new JFrame("chat");
		JFrame.setDefaultLookAndFeelDecorated(true);
		JPanel northPanel=new JPanel();  
	    JPanel southPanel=new JPanel(new BorderLayout());  
	    JScrollPane leftPanel=new JScrollPane();  
	    
	    JTextArea contentArea = new JTextArea();  
        contentArea.setEditable(false);  
        contentArea.setForeground(Color.blue);
        //contentArea.setPreferredSize(new Dimension(,30));
       
        JTextArea message = new JTextArea();  
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        JPanel mesPanel=new JPanel(new BorderLayout());
        mesPanel.add(new JScrollPane(message),"Center");
        JButton reset = new JButton("清空");  
        JButton send= new JButton("发送");  
        JButton pchat= new JButton("传送细节");
        
        southPanel.setBorder(new TitledBorder("Send Message")); 
        JPanel southeastPanel=new JPanel(new GridLayout(3,1));
        southPanel.add(mesPanel, "Center");
        southPanel.add(southeastPanel, "East");
        southeastPanel.add(send);
        southeastPanel.add(reset);
        southeastPanel.add(pchat);
        southPanel.setPreferredSize(new Dimension(600,120));
        
        JList onlinelist=new JList();
        leftPanel = new JScrollPane(onlinelist);  
        leftPanel.setBorder(new TitledBorder("Online Person"));  
        
        JScrollPane rightPanel = new JScrollPane(contentArea);  
        rightPanel.setBorder(new TitledBorder("Message Box"));
        
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,  rightPanel);  
        centerSplit.setDividerLocation(100);  
        
        JLabel label1=new JLabel("Welcome",SwingConstants.CENTER);
        northPanel.add(label1);
        
        myframe.setLayout(new BorderLayout());  
        myframe.add(northPanel, "North");  
        myframe.add(centerSplit, "Center");  
        myframe.add(southPanel, "South");  
        myframe.setSize(600, 400);  
        myframe.setVisible(true); 
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
