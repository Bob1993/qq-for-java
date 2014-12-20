package com.qq.client.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.qq.client.tools.ClientConServerThread;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqClientConServer {
	private Socket ss;
	public Socket getSs() {
		return ss;
	}

	public boolean sendLoginInfoToServer(User user)//���͵�¼��Ϣ
	{
		try {
			ss= new Socket("127.0.0.1",8888);//���ӷ�����
			ObjectOutputStream oos= new ObjectOutputStream(ss.getOutputStream());
			oos.writeObject(user);//���û���Ϣ���͸�������
			
			ObjectInputStream ois= new ObjectInputStream(ss.getInputStream());
			Message ret= (Message)ois.readObject();//������������Ϣ��
			if(ret.getMesType().equals(MessageType.login_succeed))
			{
				ClientConServerThread cst= new ClientConServerThread(ss);
				ManageClientConServerThread.addClientThread(user.getCount(), cst);
				cst.start();
				return true;
			}
			else return false;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
