package com.qq.client.tools;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class ClientConServerThread extends Thread{//将用户-->服务器的链接写成线程的形式
	private Socket socket;//该线程所拥有的客户端的socket
	public Socket getSocket() {
		return socket;
	}

	public ClientConServerThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket= socket;
	}
	
	public void run()//发送可以在任何你需要的地方发送，而接收只能在各自的线程中接受消息
	{
		while(true)
		{
			try {
				ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());
				Message message= (Message) ois.readObject();
				if(message.getMesType().equals(MessageType.common_mess))//普通消息的处理
				{
				QqChat qqChat= ManageQqChat.getQqChat(message.getReceiver()+" "+message.getSender());
				qqChat.setMessage(message);//在message中，sender是好友，receiver是自己，而qqchat聊天框是以sender+receiver的方式命名的，所以我们要把收到的信息里的接收双方反过来才能写到自己的框中
				
				}else if(message.getMesType().equals(MessageType.re_onLineFriend))//回收在线人数消息的处理
				{
					QqFriendList qqFriendList= ManageFriendList.getFriendList(message.getReceiver());
					if(qqFriendList!= null)
						qqFriendList.updateFriendList(message);	//更新该好友列表	
				}else if(message.getMesType().equals(MessageType.off_line))//因为所有的对话框都需要一对发件人--收件人才能确定，因为是服务器单方向客户端发送的消息，所以信息不全，只能依次遍历
				{
					Map<String , QqChat> qqChats= ManageQqChat.chatPool;
					Iterator it= qqChats.keySet().iterator();
					while(it.hasNext())
					{
						QqChat qqChat= qqChats.get(it.next());
						qqChat.setOffLine();
					}
				}else if(message.getMesType().equals(MessageType.sys_mess))//发布系统信息
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
