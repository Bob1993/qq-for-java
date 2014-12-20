package com.qq.client.view;

import java.awt.BorderLayout;//�����б����
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.qq.client.tools.ManageQqChat;
import com.qq.common.Message;

public class QqFriendList extends JFrame implements ActionListener,MouseListener{
	
	JPanel jphy,jpms,jphm;//��������panel���ֱ��Ǻ��ѣ�İ���ˣ�������������Ƭ
	JButton hyjb1,hyjb2,hyjb3;//������ť�ֱ��� ���ѿ�Ƭ�� ���ѣ�İ���ˣ�������������ť
	JButton msjb1,msjb2,msjb3;//������ť�ֱ��� İ���˿�Ƭ�� ���ѣ�İ���ˣ�������
	
	JPanel hyjp1,hyjp2;
	JPanel msjp1,msjp2;
	JScrollPane hyjsp,	msjsp;
	JLabel[] hyjls, msjls;
	CardLayout cl;
	String myId;
	
	public QqFriendList(String ownerId) {
		// TODO Auto-generated constructor stub
		/*
		 * ���Ų��ֵ�һ����Ƭ
		 */
		this.myId= ownerId;
		jphy= new JPanel(new BorderLayout());
		hyjb1= new JButton("����");
		hyjb1.addActionListener(this);
		hyjb2= new JButton("İ����");
		hyjb2.addActionListener(this);
		hyjb3= new JButton("������");
		jphy.add(hyjb1,"North");
		
		hyjp1= new JPanel(new GridLayout(50,1,4,4));
		hyjls=new JLabel[50];//����һ����ǩ����������ź�����
		for (int i = 0; i < hyjls.length; i++) {
			hyjls[i]= new JLabel(i+1+"",new ImageIcon("images/mm.jpg"),JLabel.LEFT);
			hyjp1.add(hyjls[i]);
			if(!(hyjls[i].getText().equals(myId)))
				hyjls[i].setEnabled(false);//ֻҪ�����Լ���ȫ������Ϊ��ɫ
			
			hyjls[i].addMouseListener(this);
		}
		hyjsp= new JScrollPane(hyjp1);
		jphy.add(hyjsp);
		
		hyjp2= new JPanel(new GridLayout(2,1));
		hyjp2.add(hyjb2);	hyjp2.add(hyjb3);
		jphy.add(hyjp2,BorderLayout.SOUTH);
		
		/*
		 * ���Ų��ֵڶ�����Ƭ
		 */
		jpms= new JPanel(new BorderLayout());
		msjb1= new JButton("����");
		msjb2= new JButton("İ����");
		msjb3= new JButton("������");
		msjb1.addActionListener(this);
		
		msjp1= new JPanel(new GridLayout(2,1));
		msjp1.add(msjb1);	msjp1.add(msjb2);
		jpms.add(msjp1,BorderLayout.NORTH);
		
		
		jpms.add(msjb3,BorderLayout.SOUTH);
		
		msjp2= new JPanel(new GridLayout(50,1,4,4));
		msjls=new JLabel[50];//����һ����ǩ�����������İ������
		for (int i = 0; i < msjls.length; i++) {
			msjls[i]= new JLabel(i+1+"",new ImageIcon("images/mm.jpg"),JLabel.LEFT);//����ͼƬ���������
			msjp2.add(msjls[i]);
			if(!(msjls[i].getText().equals(myId)))
				msjls[i].setEnabled(false);//ֻҪ�����Լ���ȫ������Ϊ��ɫ
			
			msjls[i].addMouseListener(this);
		}
		msjsp= new JScrollPane(msjp2);//����ͨ��add��jsp���������
		jpms.add(msjsp,BorderLayout.CENTER);

		cl= new CardLayout();
		this.setLayout(cl);
		this.add(jphy,"1");//��ӵ�һ����Ƭ�������1
		this.add(jpms,"2");
		this.setTitle(ownerId);
		this.setIconImage(new ImageIcon("images/qq.gif").getImage());
		this.setVisible(true);
		this.setBounds(1100, 100, 250, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateFriendList(Message mess)//����Ϣ���ǰ����е�ǰ�����û��İ�
	{
		System.out.println("ˢ����");
		String content= mess.getContent();
		String[] friend= content.split(" ");//���ÿո��ֵõ�һ����ǰ���ߺ�����
		for (int i = 0; i < friend.length; i++) {
			hyjls[(Integer.parseInt(friend[i])-1)].setEnabled(true);
			msjls[(Integer.parseInt(friend[i])-1)].setEnabled(true);
		}//ˢ�����
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2)
		{
			String friendName= ((JLabel)e.getSource()).getText();
			ManageQqChat.addQqChat(myId+" "+friendName, new QqChat(myId, friendName));
		/*	Thread thread= new Thread(new QqChat(myId, friendName));
			thread.start();//��������bug����Ϊ������������ʱ��ص��˴��ڣ������̻߳������ܣ��ٴδ򿪵�ʱ��ͳ��ִ˶Ի������߳��ظ�������
*/		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel temp= (JLabel)e.getSource();
		temp.setForeground(Color.black);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel temp= (JLabel)e.getSource();
		temp.setForeground(Color.red);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==msjb1)
		{
			cl.show(this.getContentPane(), "1");
		}else if(e.getSource()==hyjb2)
		{
			cl.show(this.getContentPane(), "2");
		}
	}

}
