package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

import com.qq.client.view.QqChat;

public class ManageQqChat {
public static Map<String ,QqChat> chatPool= new HashMap<>();//创建一个聊天窗口池
	
	public static void addQqChat(String senderAndReceiver, QqChat qqChat)//以用户帐户--线程的方法来进行二者的匹配
	{
		chatPool.put(senderAndReceiver, qqChat);
	}
	
	public static QqChat getQqChat(String senderAndReceiver)//实现用用户名来获取当前用户的通信线程
	{
		return chatPool.get(senderAndReceiver);
	}
}
