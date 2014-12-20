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
 * qq�Ŀͻ��˵�½����
 */
public class QqClientLogin extends JFrame implements ActionListener{
	
		JLabel jl1,jl2,jl3,jl4,jl5;
		JPanel jp1,jp2,jp3,jp4;
		JButton jb1,jb2,jb3,jb4;
		JTextField jtf;//�˺������
		JCheckBox jcb1,jcb2;
		JPasswordField jpf;//���������
		JTabbedPane jtp;
		public QqClientLogin() {
			jb1= new JButton(new ImageIcon("images/denglu.gif"));//��¼��ť
			jb1.addActionListener(this);
			jb2= new JButton(new ImageIcon("images/quxiao.gif"));//ȡ����ť
			jb3= new JButton(new ImageIcon("images/xiangdao.gif"));	//ע���򵼰�ť
			jb3.addActionListener(this);
			jb4= new JButton(new ImageIcon("images/clear.gif"));//������밴ť

			jl1= new JLabel(new ImageIcon("images/tou.gif"));//����������ߵ�һ��ͼƬ
			this.add(jl1,BorderLayout.NORTH);
			
			jp4= new JPanel();//frame�ϱߵ�һ��panel
			jp4.add(jb1);	jp4.add(jb2);	jp4.add(jb3);
			this.add(jp4,BorderLayout.SOUTH);
			
			jp2= new JPanel();//�м䲿�ֵĲ���
			jp2.setName("QQ��¼");
			jp2.setLayout(new GridLayout(3,3,4,4));
			jpf= new JPasswordField(12);
			jtf= new JTextField(12);
			jcb1= new JCheckBox("�����½");//������ѡ��
			jcb2= new JCheckBox("��ס����");
			jl2= new JLabel("QQ����",JLabel.CENTER);	
			jl3= new JLabel("QQ����",JLabel.CENTER);	
			jl4= new JLabel("��������",JLabel.CENTER);
			jl4.setFont(new Font("����",Font.PLAIN,16));
			jl4.setForeground(Color.blue);
			jl5= new JLabel("<html><a href='www.qq.com'>�������뱣��</a></html>");
			jl5.setFont(new Font("����",Font.PLAIN,14));
			jl5.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR));//����ƶ�����λ�ñ����
			jp2.add(jl2);	jp2.add(jtf);	jp2.add(jb4);	jp2.add(jl3);	jp2.add(jpf);	jp2.add(jl4);
			jp2.add(jcb1);	jp2.add(jcb2);	jp2.add(jl5);
			
			jtp= new JTabbedPane();
			jp1= new JPanel();	jp1.setName("�ֻ��ŵ�½");
			jp3= new JPanel();	jp3.setName("�����¼");
			jtp.add(jp2);	jtp.add(jp3);	jtp.add(jp1);
			this.add(jtp);
			
			// TODO Auto-generated constructor stub
			this.setIconImage(new ImageIcon("images/qq.gif").getImage());
			this.setTitle("QQ��¼");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setBounds(200, 200, 350, 240);
			this.setResizable(false);
		}
		public static void main(String[] args) {//�ͻ��˵�¼���
			QqClientLogin ql= new QqClientLogin();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==jb1)
			{
				User user= new User(jtf.getText(), new String (jpf.getPassword()));//jpf.getText()
				Boolean result= new QqClientUser().check(user);
				if(result)//��֤��½�ɹ�
				{
					this.dispose();
					QqFriendList friendList= new QqFriendList(user.getCount());//����һ�������б�
					ManageFriendList.addFriendList(user.getCount(), friendList);
					
					try {
						Message message= new Message();//����һ��������Ϣ�����
						message.setMesType(MessageType.get_onLineFriend);
						message.setSender(user.getCount());//���ð�����Ϣ
						
						ObjectOutputStream oos= new ObjectOutputStream(ManageClientConServerThread.getClientThread(user.getCount()).getSocket().getOutputStream());
						oos.writeObject(message);//�����Լ����̵߳�socket�������׵���Ϣ
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else JOptionPane.showMessageDialog(this, "�������");
			}
			else if(e.getSource()==jb3)
			{
				new QqRegister();
			}
		}
}
