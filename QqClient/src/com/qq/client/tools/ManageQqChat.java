/**
 * 这是一个管理用户聊天界面的类
 */
package com.qq.client.tools;

import java.util.*;

import javax.swing.JFrame;

import com.qq.client.view.*;
public class ManageQqChat {

	private static HashMap hm=new HashMap<String, JFrame>();
	
	//加入
	public static void addQqChat(String loginIdAnFriendId,QqChat qqChat)
	{
		hm.put(loginIdAnFriendId, qqChat);
	}
	//取出
	public static QqChat getQqChat(String loginIdAnFriendId )
	{
		return (QqChat)hm.get(loginIdAnFriendId);
	}
	public static void RemoveQqChat(String loginIdAnFriendId)
	{
		hm.remove(loginIdAnFriendId);
	}
	public static void addQunChat(String loginIdAnFriendId,QunChat qqChat)
	{
		hm.put(loginIdAnFriendId, qqChat);
	}
	//取出
	public static QunChat getQunChat(String loginIdAnFriendId )
	{
		return (QunChat)hm.get(loginIdAnFriendId);
	}
	public static void RemoveQunChat(String loginIdAnFriendId)
	{
		hm.remove(loginIdAnFriendId);
	}
	
}
