package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

public class ManageClientConServerThread {
public static Map<String,ClientConServerThread> threadPool= new HashMap<>();//����һ���̳߳�,��������ͻ���-->���������ͨ���߳�
	
	public static void addClientThread(String userCount, ClientConServerThread thread)//���û��ʻ�--�̵߳ķ��������ж��ߵ�ƥ��
	{
		threadPool.put(userCount, thread);
	}
	
	public static ClientConServerThread getClientThread(String userCount)//ʵ�����û�������ȡ��ǰ�û���ͨ���߳�
	{
		return threadPool.get(userCount);
	}

}
