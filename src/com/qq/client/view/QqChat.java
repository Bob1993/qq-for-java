package com.qq.client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qq.client.model.QqClientConServer;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class QqChat extends JFrame implements ActionListener{//QQ聊天窗口
	JScrollPane jsp;
	JTextArea jta;
	JTextField jtf;
	JButton jb1;
	JPanel jp;
	String myId,friend;//好友信息
	public QqChat(String myId, String friend) {
		// TODO Auto-generated constructor st
		this.myId= myId;
		this.friend= friend;
		jta= new JTextArea();
		jta.setEditable(false);
		jsp= new JScrollPane(jta);
		this.add(jsp);
		
		jb1= new JButton("发送");
		jb1.addActionListener(this);
		jtf= new JTextField(15);
		jp= new JPanel();
		jp.add(jtf);	jp.add(jb1);
		this.add(jp,BorderLayout.SOUTH);
		
		this.setTitle(myId+"正在和"+friend+"聊天");
		this.setIconImage(new ImageIcon("images/qq.gif").getImage());
		this.setVisible(true);
		this.setBounds(500, 200, 350, 300);
	}
	
	public void setMessage(Message message)
	{
		if(message.getMesType().equals(MessageType.common_mess))
			jta.append(message.getSender()+"说："+message.getContent()+"\n");
		else if(message.getMesType().equals(MessageType.sys_mess))
			jta.append(message.getContent());
	}
	
	public void setOffLine()
	{
		jb1.setEnabled(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== jb1)//发送消息
		{
			Message message= new Message();
			message.setContent(jtf.getText());
			jtf.setText("");
			message.setDate(new Date().toString());
			message.setSender(myId);
			message.setReceiver(friend);
			message.setMesType(MessageType.common_mess);
			try {
				ObjectOutputStream oos= new ObjectOutputStream(ManageClientConServerThread.getClientThread(myId).getSocket().getOutputStream());
				oos.writeObject(message);
				jta.append("我说："+message.getContent()+"\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
