package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

import com.qq.client.view.QqFriendList;

public class ManageFriendList {
public static Map<String,QqFriendList> listPool= new HashMap<>();//����һ���б��ϣ������������еĺ����б�
	
	public static void addFriendList(String userCount, QqFriendList qqFriendList){
		listPool.put(userCount, qqFriendList);
	}
	
	public static QqFriendList getFriendList(String userCount)//ʵ�����û�������ȡ��ǰ�û���ͨ���߳�
	{
		return listPool.get(userCount);
	}
}
