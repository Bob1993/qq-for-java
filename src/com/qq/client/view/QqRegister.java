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
	JLabel jl1,jl2,jl3;//��jtfһһ��Ӧ
	JButton jb1,jb2;//�ύ��ť��ȡ����ť
	JPanel jp1,jp2;
	public QqRegister()
	{
		jtf= new JTextField(10);
		jl1= new JLabel("�û��ʺ�", JLabel.CENTER);
		
		jpf1= new JPasswordField(10);
		jl2= new JLabel("�û�����", JLabel.CENTER);
		
		jpf2= new JPasswordField(10);
		jl3= new JLabel("ȷ������", JLabel.CENTER);
		
		jp1= new JPanel(new GridLayout(3,2,10,5));
		jp1.add(jl1);	jp1.add(jtf);	jp1.add(jl2);	jp1.add(jpf1);
		jp1.add(jl3);	jp1.add(jpf2);
		
		jb1= new JButton("ȷ���ύ");
		jb2= new JButton("ȡ��");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jp2= new JPanel();
		jp2.add(jb1);	jp2.add(jb2);
		
		this.add(jp1);
		this.add(jp2,BorderLayout.SOUTH);
		this.setTitle("�û���Ϣע��");
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
		if(e.getSource()== jb1)//�ύ��ť
		{
			if(new String(jpf2.getPassword()).equals (new String(jpf1.getPassword())))
			{
			User user= new User(jtf.getText(), new String(jpf1.getPassword()));
			user.setType("register");
			try {
				Socket ss= new Socket("127.0.0.1",8888);//ע������ӷ�����
				ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
				oos.writeObject(user);//���û�ע����Ϣ���͸�������
				
				ObjectInputStream ois= new ObjectInputStream(ss.getInputStream());
				Message reg= (Message)ois.readObject();//ע��֮��ķ�����Ϣ
				
				if(reg.getMesType().equals(MessageType.regist_succeed))
				{
					JOptionPane.showMessageDialog(this, "ע��ɹ�");
				}
				else JOptionPane.showMessageDialog(this, "ע��ʧ��");
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			else JOptionPane.showMessageDialog(this, "�������벻һ��");
		}
		else{
			this.dispose();
		}
	}
}
