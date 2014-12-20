package com.qq.client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.qq.common.*;

import javax.swing.*;

import com.qq.client.model.QqClientUser;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.client.tools.ManageFriendList;
/*
 * qq的客户端登陆界面
 */
public class QqClientLogin extends JFrame implements ActionListener{
	
		JLabel jl1,jl2,jl3,jl4,jl5;
		JPanel jp1,jp2,jp3,jp4;
		JButton jb1,jb2,jb3,jb4;
		JTextField jtf;//账号输入框
		JCheckBox jcb1,jcb2;
		JPasswordField jpf;//密码输入框
		JTabbedPane jtp;
		public QqClientLogin() {
			jb1= new JButton(new ImageIcon("images/denglu.gif"));//登录按钮
			jb1.addActionListener(this);
			jb2= new JButton(new ImageIcon("images/quxiao.gif"));//取消按钮
			jb3= new JButton(new ImageIcon("images/xiangdao.gif"));	//注册向导按钮
			jb3.addActionListener(this);
			jb4= new JButton(new ImageIcon("images/clear.gif"));//清除密码按钮

			jl1= new JLabel(new ImageIcon("images/tou.gif"));//设置整体最北边的一张图片
			this.add(jl1,BorderLayout.NORTH);
			
			jp4= new JPanel();//frame南边的一个panel
			jp4.add(jb1);	jp4.add(jb2);	jp4.add(jb3);
			this.add(jp4,BorderLayout.SOUTH);
			
			jp2= new JPanel();//中间部分的布局
			jp2.setName("QQ登录");
			jp2.setLayout(new GridLayout(3,3,4,4));
			jpf= new JPasswordField(12);
			jtf= new JTextField(12);
			jcb1= new JCheckBox("隐身登陆");//两个复选框
			jcb2= new JCheckBox("记住密码");
			jl2= new JLabel("QQ号码",JLabel.CENTER);	
			jl3= new JLabel("QQ密码",JLabel.CENTER);	
			jl4= new JLabel("忘记密码",JLabel.CENTER);
			jl4.setFont(new Font("楷体",Font.PLAIN,16));
			jl4.setForeground(Color.blue);
			jl5= new JLabel("<html><a href='www.qq.com'>申请密码保护</a></html>");
			jl5.setFont(new Font("楷体",Font.PLAIN,14));
			jl5.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR));//鼠标移动到该位置变成手
			jp2.add(jl2);	jp2.add(jtf);	jp2.add(jb4);	jp2.add(jl3);	jp2.add(jpf);	jp2.add(jl4);
			jp2.add(jcb1);	jp2.add(jcb2);	jp2.add(jl5);
			
			jtp= new JTabbedPane();
			jp1= new JPanel();	jp1.setName("手机号登陆");
			jp3= new JPanel();	jp3.setName("邮箱登录");
			jtp.add(jp2);	jtp.add(jp3);	jtp.add(jp1);
			this.add(jtp);
			
			// TODO Auto-generated constructor stub
			this.setIconImage(new ImageIcon("images/qq.gif").getImage());
			this.setTitle("QQ登录");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setBounds(200, 200, 350, 240);
			this.setResizable(false);
		}
		public static void main(String[] args) {//客户端登录入口
			QqClientLogin ql= new QqClientLogin();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jb1)
			{
				User user= new User(jtf.getText(), new String (jpf.getPassword()));//jpf.getText()
				Boolean result= new QqClientUser().check(user);
				if(result)//验证登陆成功
				{
					this.dispose();
					QqFriendList friendList= new QqFriendList(user.getCount());//创建一个好友列表
					ManageFriendList.addFriendList(user.getCount(), friendList);
					
					try {
						Message message= new Message();//发送一个在线信息请求包
						message.setMesType(MessageType.get_onLineFriend);
						message.setSender(user.getCount());//设置包内信息
						
						ObjectOutputStream oos= new ObjectOutputStream(ManageClientConServerThread.getClientThread(user.getCount()).getSocket().getOutputStream());
						oos.writeObject(message);//利用自己的线程的socket来发对套的信息
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else JOptionPane.showMessageDialog(this, "密码错误");
			}
			else if(e.getSource()==jb3)
			{
				new QqRegister();
			}
		}
}
