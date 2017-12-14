/**
 * ����һ�������û�����������
 */
package com.qq.client.tools;

import java.util.*;

import javax.swing.JFrame;

import com.qq.client.view.*;
public class ManageQqChat {

	private static HashMap hm=new HashMap<String, JFrame>();
	
	//����
	public static void addQqChat(String loginIdAnFriendId,QqChat qqChat)
	{
		hm.put(loginIdAnFriendId, qqChat);
	}
	//ȡ��
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
	//ȡ��
	public static QunChat getQunChat(String loginIdAnFriendId )
	{
		return (QunChat)hm.get(loginIdAnFriendId);
	}
	public static void RemoveQunChat(String loginIdAnFriendId)
	{
		hm.remove(loginIdAnFriendId);
	}
	
}
