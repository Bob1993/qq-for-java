package com.qq.client.tools;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class ClientConServerThread extends Thread{//���û�-->������������д���̵߳���ʽ
	private Socket socket;//���߳���ӵ�еĿͻ��˵�socket
	public Socket getSocket() {
		return socket;
	}

	public ClientConServerThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket= socket;
	}
	
	public void run()//���Ϳ������κ�����Ҫ�ĵط����ͣ�������ֻ���ڸ��Ե��߳��н�����Ϣ
	{
		while(true)
		{
			try {
				ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
				Message message= (Message) ois.readObject();
				if(message.getMesType().equals(MessageType.common_mess))//��ͨ��Ϣ�Ĵ���
				{
				QqChat qqChat= ManageQqChat.getQqChat(message.getReceiver()+" "+message.getSender());
				qqChat.setMessage(message);//��message�У�sender�Ǻ��ѣ�receiver���Լ�����qqchat���������sender+receiver�ķ�ʽ�����ģ���������Ҫ���յ�����Ϣ��Ľ���˫������������д���Լ��Ŀ���
				
				}else if(message.getMesType().equals(MessageType.re_onLineFriend))//��������������Ϣ�Ĵ���
				{
					QqFriendList qqFriendList= ManageFriendList.getFriendList(message.getReceiver());
					if(qqFriendList!= null)
						qqFriendList.updateFriendList(message);	//���¸ú����б�	
				}else if(message.getMesType().equals(MessageType.off_line))//��Ϊ���еĶԻ�����Ҫһ�Է�����--�ռ��˲���ȷ������Ϊ�Ƿ�����������ͻ��˷��͵���Ϣ��������Ϣ��ȫ��ֻ�����α���
				{
					Map<String , QqChat> qqChats= ManageQqChat.chatPool;
					Iterator it= qqChats.keySet().iterator();
					while(it.hasNext())
					{
						QqChat qqChat= qqChats.get(it.next());
						qqChat.setOffLine();
					}
				}else if(message.getMesType().equals(MessageType.sys_mess))//����ϵͳ��Ϣ
				{
					Map<String , QqChat> qqChats= ManageQqChat.chatPool;
					Iterator it= qqChats.keySet().iterator();
					while(it.hasNext())
					{
						QqChat qqChat= qqChats.get(it.next());
						qqChat.setMessage(message);
					}
				}
				
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
