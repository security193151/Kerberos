package UI;
//聊天界面
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class frame2 implements MouseListener {
	private JFrame myframe=new JFrame("chat");
	private JButton reset = new JButton("清空");  
    private JButton send= new JButton("发送");  
    private JButton out= new JButton("退出登录");
    private JTextArea message = new JTextArea();  //发送
    private JTextArea contentArea = new JTextArea();  //消息显示
    private JTextArea onlinelist=new JTextArea();//在线用户
    private JTextArea MessageDetail=new JTextArea();//消息细节
    private JLabel user1=new JLabel();
    private JLabel user2=new JLabel();
    private JLabel user3=new JLabel();
    private JLabel user4=new JLabel();
    private JLabel prelable=new JLabel();//用于鼠标事件中，指示前一个被点击的标签
    ArrayList<String> online=new ArrayList<String>();
    ArrayList<JLabel> onlineb=new ArrayList<JLabel>();
	public frame2(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		online.add("Jiemy1");
		online.add("Jiemy2");
		online.add("Jiemy3");
		online.add("Jiemy4");
		onlineb.add(user1);
		onlineb.add(user2);
		onlineb.add(user3);
		onlineb.add(user4);
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
        
        //onlinelist.setEditable(false);
        //onlinelist.setLineWrap(true);
        //onlinelist.setWrapStyleWord(true);
       // leftPanel = new JScrollPane(onlinelist);  
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
        centerSplit.setDividerLocation(110);  
        
        JLabel label1=new JLabel("Welcome",SwingConstants.CENTER);
        northPanel.add(label1);
       
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
                System.out.println("gfyusdguysdjnuisdyuh");;
            }
        });
        
        for(int i=0;i<4;i++){
        	onlineb.get(i).setText(online.get(i));
        	System.out.println(onlineb.get(i).getText());
        	System.out.println(online.get(i));
        	//onlinelist.setText("hello");
        	leftPanel.add(onlineb.get(i));
        	
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getClickCount()==2){
			prelable.setBackground(Color.WHITE);
			((JLabel)e.getComponent()).setBackground(Color.LIGHT_GRAY);
			prelable=(JLabel)e.getComponent();
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String []args){
		frame2 f=new frame2();
	}
}