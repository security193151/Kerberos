package UI;
import java.awt.Graphics;  
import java.awt.Image;  
import java.io.File;  
  
import javax.swing.ImageIcon;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
  
import java.awt.Image;  

import javax.swing.ImageIcon;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
  
import java.awt.FlowLayout;  

import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
  
public class JFrameBackground {  
  
 private JFrame frame = new JFrame("����ͼƬ����");  
  
 private JPanel imagePanel;  
  
 private ImageIcon background;  
  
 public static void main(String[] args) {  
  new JFrameBackground();  
 }  
  
 public JFrameBackground() {  
  background = new ImageIcon("2.jpg");// ����ͼƬ  
  JLabel label = new JLabel(background);// �ѱ���ͼƬ��ʾ��һ����ǩ����  
  // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������  
  label.setBounds(0, 0, background.getIconWidth(),  
    background.getIconHeight());  
  // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��  
  imagePanel = (JPanel) frame.getContentPane();  
  imagePanel.setOpaque(false);  
  // ���ݴ���Ĭ�ϵĲ��ֹ�����ΪBorderLayout  
  imagePanel.setLayout(new FlowLayout());  
  imagePanel.add(new JButton("���԰�ť"));  
  
  frame.getLayeredPane().setLayout(null);  
  // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
  frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  frame.setSize(background.getIconWidth(), background.getIconHeight());  
  frame.setResizable(false);  
  frame.setVisible(true);  
 }  
} 

