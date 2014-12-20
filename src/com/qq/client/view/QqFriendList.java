package com.qq.client.view;

import java.awt.BorderLayout;//好友列表界面
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
	
	JPanel jphy,jpms,jphm;//定义三个panel，分别是好友，陌生人，黑名单三个卡片
	JButton hyjb1,hyjb2,hyjb3;//三个按钮分别是 好友卡片的 好友，陌生人，黑名单三个按钮
	JButton msjb1,msjb2,msjb3;//三个按钮分别是 陌生人卡片的 好友，陌生人，黑名单
	
	JPanel hyjp1,hyjp2;
	JPanel msjp1,msjp2;
	JScrollPane hyjsp,	msjsp;
	JLabel[] hyjls, msjls;
	CardLayout cl;
	String myId;
	
	public QqFriendList(String ownerId) {
		// TODO Auto-generated constructor stub
		/*
		 * 安排布局第一个卡片
		 */
		this.myId= ownerId;
		jphy= new JPanel(new BorderLayout());
		hyjb1= new JButton("好友");
		hyjb1.addActionListener(this);
		hyjb2= new JButton("陌生人");
		hyjb2.addActionListener(this);
		hyjb3= new JButton("黑名单");
		jphy.add(hyjb1,"North");
		
		hyjp1= new JPanel(new GridLayout(50,1,4,4));
		hyjls=new JLabel[50];//定义一个标签数组用来存放好友们
		for (int i = 0; i < hyjls.length; i++) {
			hyjls[i]= new JLabel(i+1+"",new ImageIcon("images/mm.jpg"),JLabel.LEFT);
			hyjp1.add(hyjls[i]);
			if(!(hyjls[i].getText().equals(myId)))
				hyjls[i].setEnabled(false);//只要不是自己，全部设置为灰色
			
			hyjls[i].addMouseListener(this);
		}
		hyjsp= new JScrollPane(hyjp1);
		jphy.add(hyjsp);
		
		hyjp2= new JPanel(new GridLayout(2,1));
		hyjp2.add(hyjb2);	hyjp2.add(hyjb3);
		jphy.add(hyjp2,BorderLayout.SOUTH);
		
		/*
		 * 安排布局第二个卡片
		 */
		jpms= new JPanel(new BorderLayout());
		msjb1= new JButton("好友");
		msjb2= new JButton("陌生人");
		msjb3= new JButton("黑名单");
		msjb1.addActionListener(this);
		
		msjp1= new JPanel(new GridLayout(2,1));
		msjp1.add(msjb1);	msjp1.add(msjb2);
		jpms.add(msjp1,BorderLayout.NORTH);
		
		
		jpms.add(msjb3,BorderLayout.SOUTH);
		
		msjp2= new JPanel(new GridLayout(50,1,4,4));
		msjls=new JLabel[50];//定义一个标签数组用来存放陌生人们
		for (int i = 0; i < msjls.length; i++) {
			msjls[i]= new JLabel(i+1+"",new ImageIcon("images/mm.jpg"),JLabel.LEFT);//设置图片在文字左侧
			msjp2.add(msjls[i]);
			if(!(msjls[i].getText().equals(myId)))
				msjls[i].setEnabled(false);//只要不是自己，全部设置为灰色
			
			msjls[i].addMouseListener(this);
		}
		msjsp= new JScrollPane(msjp2);//不能通过add给jsp里加入内容
		jpms.add(msjsp,BorderLayout.CENTER);

		cl= new CardLayout();
		this.setLayout(cl);
		this.add(jphy,"1");//添加第一个卡片，并标记1
		this.add(jpms,"2");
		this.setTitle(ownerId);
		this.setIconImage(new ImageIcon("images/qq.gif").getImage());
		this.setVisible(true);
		this.setBounds(1100, 100, 250, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateFriendList(Message mess)//该消息包是包含有当前在线用户的包
	{
		System.out.println("刷新了");
		String content= mess.getContent();
		String[] friend= content.split(" ");//利用空格拆分得到一个当前在线好友组
		for (int i = 0; i < friend.length; i++) {
			hyjls[(Integer.parseInt(friend[i])-1)].setEnabled(true);
			msjls[(Integer.parseInt(friend[i])-1)].setEnabled(true);
		}//刷新完毕
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2)
		{
			String friendName= ((JLabel)e.getSource()).getText();
			ManageQqChat.addQqChat(myId+" "+friendName, new QqChat(myId, friendName));
		/*	Thread thread= new Thread(new QqChat(myId, friendName));
			thread.start();//这里会出现bug，因为如果正在聊天的时候关掉了窗口，但是线程还是在跑，再次打开的时候就出现此对话窗口线程重复的现象
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
