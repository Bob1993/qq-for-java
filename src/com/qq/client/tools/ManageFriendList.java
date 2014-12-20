package com.qq.client.tools;

import java.util.HashMap;
import java.util.Map;

import com.qq.client.view.QqFriendList;

public class ManageFriendList {
public static Map<String,QqFriendList> listPool= new HashMap<>();//创建一个列表集合，用来管理所有的好友列表
	
	public static void addFriendList(String userCount, QqFriendList qqFriendList){
		listPool.put(userCount, qqFriendList);
	}
	
	public static QqFriendList getFriendList(String userCount)//实现用用户名来获取当前用户的通信线程
	{
		return listPool.get(userCount);
	}
}
