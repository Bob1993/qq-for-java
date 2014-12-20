package com.qq.client.model;

import com.qq.common.User;

public class QqClientUser {
	public boolean check(User user)
	{	
		return new QqClientConServer().sendLoginInfoToServer(user);
	}
}
