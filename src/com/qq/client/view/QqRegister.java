package com.qq.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqRegister extends JFrame implements ActionListener{
	JTextField jtf;
	JPasswordField jpf1,jpf2;
	JLabel jl1,jl2,jl3;//和jtf一一对应
	JButton jb1,jb2;//提交按钮，取消按钮
	JPanel jp1,jp2;
	public QqRegister()
	{
		jtf= new JTextField(10);
		jl1= new JLabel("用户帐号", JLabel.CENTER);
		
		jpf1= new JPasswordField(10);
		jl2= new JLabel("用户密码", JLabel.CENTER);
		
		jpf2= new JPasswordField(10);
		jl3= new JLabel("确认密码", JLabel.CENTER);
		
		jp1= new JPanel(new GridLayout(3,2,10,5));
		jp1.add(jl1);	jp1.add(jtf);	jp1.add(jl2);	jp1.add(jpf1);
		jp1.add(jl3);	jp1.add(jpf2);
		
		jb1= new JButton("确认提交");
		jb2= new JButton("取消");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jp2= new JPanel();
		jp2.add(jb1);	jp2.add(jb2);
		
		this.add(jp1);
		this.add(jp2,BorderLayout.SOUTH);
		this.setTitle("用户信息注册");
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBounds(250, 240, 250, 170);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		new QqRegister();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== jb1)//提交按钮
		{
			if(new String(jpf2.getPassword()).equals (new String(jpf1.getPassword())))
			{
			User user= new User(jtf.getText(), new String(jpf1.getPassword()));
			user.setType("register");
			try {
				Socket ss= new Socket("127.0.0.1",8888);//注册端连接服务器
				ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
				oos.writeObject(user);//将用户注册信息发送给服务器
				
				ObjectInputStream ois= new ObjectInputStream(ss.getInputStream());
				Message reg= (Message)ois.readObject();//注册之后的反馈信息
				
				if(reg.getMesType().equals(MessageType.regist_succeed))
				{
					JOptionPane.showMessageDialog(this, "注册成功");
				}
				else JOptionPane.showMessageDialog(this, "注册失败");
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			else JOptionPane.showMessageDialog(this, "两个密码不一致");
		}
		else{
			this.dispose();
		}
	}
}
