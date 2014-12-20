package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

public class ManageClientConServerThread {
public static Map<String,ClientConServerThread> threadPool= new HashMap<>();//创建一个线程池,用来管理客户端-->服务器间的通信线程
	
	public static void addClientThread(String userCount, ClientConServerThread thread)//以用户帐户--线程的方法来进行二者的匹配
	{
		threadPool.put(userCount, thread);
	}
	
	public static ClientConServerThread getClientThread(String userCount)//实现用用户名来获取当前用户的通信线程
	{
		return threadPool.get(userCount);
	}

}
