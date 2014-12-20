package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

import com.qq.client.view.QqChat;

public class ManageQqChat {
public static Map<String ,QqChat> chatPool= new HashMap<>();//����һ�����촰�ڳ�
	
	public static void addQqChat(String senderAndReceiver, QqChat qqChat)//���û��ʻ�--�̵߳ķ��������ж��ߵ�ƥ��
	{
		chatPool.put(senderAndReceiver, qqChat);
	}
	
	public static QqChat getQqChat(String senderAndReceiver)//ʵ�����û�������ȡ��ǰ�û���ͨ���߳�
	{
		return chatPool.get(senderAndReceiver);
	}
}
