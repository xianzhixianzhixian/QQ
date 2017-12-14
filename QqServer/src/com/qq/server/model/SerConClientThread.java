/**
 * 功能：是服务器和某个客户端的通信线程
 */
package com.qq.server.model;

import java.sql.ResultSet;
import java.util.*;
import java.net.*;
import java.awt.Color;
import java.io.*;

import javax.swing.JPanel;

import com.qq.common.*;

public class SerConClientThread extends Thread {

	public Socket s;
	ResultSet rs = null, rs2 = null;
	LinkClass link = new LinkClass();

	public SerConClientThread(Socket s) {
		
		// 把服务器和该客户端的连接赋给s
		this.s = s;
	}

	// 让该线程去通知其它用户
	public void notifyOther(String iam) {
		
		// 得到所有在线的人的线程
		HashMap hm = ManageClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			Message m = new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);
			// 取出在线人的id
			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageClientThread.getClientThread(onLineUserId).s
								.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	public void notifyunloadOther(String iam) {
		
		// 得到所有在线的人的线程
		HashMap hm = ManageClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			
			Message m = new Message();
			m.setCon(iam);
			m.setMesType(MessageType.messgae_ret_unloadFriends);
			// 取出在线人的id
			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageClientThread.getClientThread(onLineUserId).s
								.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	public void run() {

		while (true) {

			// 这里该线程就可以接收客户端的信息.
			try {
				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());
				Message m = (Message) ois.readObject();

				// System.out.println(m.getSender()+" 给 "+m.getGetter()+" 说:"+m.getCon());

				// 对从客户端取得的消息进行类型判断，然后做相应的处理
				if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// 一会完成转发.
					// 取得接收人的通信线程
					if (m.getCon() != null) {
						String sql = " insert into ChatContent(Sender,Content,Receiver,Times,font,size,color) values('"
								+ m.getSender()
								+ "','"
								+ m.getCon()
								+ "','"
								+ m.getGetter()
								+ "','"
								+ m.getSendTime()
								+ "','"
								+ m.getFont()
								+ "','"
								+ m.getSize()
								+ "','" + m.getCol() + "')";

						link.Insert(sql);
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
					}
				} else if (m.getMesType().equals(MessageType.message_shake)) {
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m);
				} else if (m.getMesType().equals(
						MessageType.message_get_onLineFriend)) {

					// 把在服务器的好友给该客户端返回.
					String res = ManageClientThread.getAllOnLineUserid();
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(
							s.getOutputStream());
					oos.writeObject(m2);
				} else if (m.getMesType().equals(
						MessageType.message_ret_addFriend)) {

					// 把在服务器的好友给该客户端返回.
					// ////////////////请求好友添加进数据库
					String sql = " insert into AddFriend(UserId,FriendId,PS) values('"
							+ m.getSender()
							+ "','"
							+ m.getGetter()
							+ "','"
							+ "我是。。。" + "')";
					link.Insert(sql);
					// ////////////////////////
					sql = "select * from Login where UserId='" + m.getGetter()
							+ "'";
					rs = link.doSelect(sql);
					if (rs.next()) {

						Message m2 = new Message();
						m2.setMesType(MessageType.message_addpoint);
						m2.setCon(m.getGetter());
						m2.setGetter(m.getGetter());
						m2.setSender(m.getSender());
						sql = "select * from Login where UserId='"
								+ m.getSender() + "'";
						rs = link.doSelect(sql);
						if (rs.next())
							m2.setFriname(rs.getString("name") + "("
									+ m.getSender() + ")");
						System.out.println("m2.setFriname " + m2.getFriname());
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m2);
					} else {
						Message m2 = new Message();
						m2.setMesType(MessageType.message_ret_addFriend);
						m2.setCon(null);
						m2.setGetter(m.getSender());
						SerConClientThread sc = ManageClientThread
								.getClientThread(m2.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m2);
					}

				} else if (m.getMesType().equals(MessageType.message_acceptadd)) {
					// 同意好友请求
					String sql = "select * from Login where UserId='"
							+ m.getGetter() + "'";
					String sql_insert;
					rs = link.doSelect(sql);
					if (rs.next()) {
						sql_insert = "insert into friendlist values('"
								+ m.getGetter() + "','" + m.getSender()
								+ "','同学','2','" + rs.getString("name") + "')";
						link.Insert(sql_insert);
					}

					sql = "select * from Login where UserId='" + m.getSender()
							+ "'";
					rs = link.doSelect(sql);
					if (rs.next()) {
						sql_insert = "insert into friendlist values('"
								+ m.getSender() + "','" + m.getGetter()
								+ "','同学','2','" + rs.getString("name") + "')";
						link.Insert(sql_insert);
					}
					sql_insert = "update friendlist set fenzucount = '2' where mqq = '"
							+ m.getGetter() + "'";
					link.Insert(sql_insert);
					sql_insert = "update friendlist set fenzucount = '2' where mqq = '"
							+ m.getSender() + "'";
					link.Insert(sql_insert);
					Message m2 = new Message();
					m2.setMesType(MessageType.message_acceptadd);
					m2.setGetter(m.getGetter());
					m2.setSender(m.getSender());
					m2.setFriname(rs.getString("name") + "(" + m.getSender()
							+ ")");
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getGetter());
					System.out.println("调用socket  " + m.getSender());
					if (sc == null) {
						System.out.println("调用socket 是空的 ");
					}
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m2);
				}

				else if (m.getMesType().equals(MessageType.message_jujueadd)) {

					Message m2 = new Message();
					m2.setMesType(MessageType.message_jujueadd);
					m2.setGetter(m.getGetter());
					m2.setSender(m.getSender());
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getGetter());

					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m2);
				} else if (m.getMesType().equals(
						MessageType.message_ret_oldMessage)) {
					String sql = "select * from ChatContent where Sender='"
							+ m.getSender() + "'and Receiver='" + m.getGetter()
							+ "'or Sender='" + m.getGetter()
							+ "'and Receiver='" + m.getSender() + "'";
					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];

						rs.first();

						m.setFont(rs.getString("font"));
						m.setSendTime(rs.getString("Times"));
						m.setSize(rs.getInt("size"));
						m.setCol(rs.getString("color"));
						m.setCon(rs.getString(4));
						m.setFriname(rs.getString("Sender"));
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
						while (rs.next()) {
							m.setFont(rs.getString("font"));
							m.setSendTime(rs.getString("Times"));
							m.setSize(rs.getInt("size"));
							m.setCol(rs.getString("color"));
							m.setCon(rs.getString(4));
							m.setFriname(rs.getString("Sender"));

							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);
						}

					}
				} else if (m.getMesType().equals(
						MessageType.message_ret_outlineMessage)) {
					String sql = "select *from ChatContent where Times>(select OutlineTime from Outline where UserId='"
							+ m.getSender()
							+ "') and Receiver='"
							+ m.getSender()
							+ "' and Sender='"
							+ m.getGetter()
							+ "'";
					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];

						rs.first();
						m.setFont(rs.getString("font"));
						m.setSendTime(rs.getString("Times"));
						m.setSize(rs.getInt("size"));
						m.setCol(rs.getString("color"));
						m.setCon(rs.getString(4));

						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
						while (rs.next()) {
							m.setFont(rs.getString("font"));
							m.setSendTime(rs.getString("Times"));
							m.setSize(rs.getInt("size"));
							m.setCol(rs.getString("color"));
							m.setCon(rs.getString(4));

							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);
						}

					}
				} else if (m.getMesType().equals(
						MessageType.message_ret_pointMessage)) {
					String sql = "select *from ChatContent where Times>(select OutlineTime from Outline where UserId='"
							+ m.getSender()
							+ "') and Receiver='"
							+ m.getSender() + "'";
					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];

						rs.first();

						m.setFont(rs.getString("font"));
						m.setSendTime(rs.getString("Times"));
						m.setSize(rs.getInt("size"));
						m.setCol(rs.getString("color"));
						m.setCon(rs.getString(4));
						m.setGetter(rs.getString(1));
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);

						while (rs.next()) {
							m.setGetter(rs.getString(1));
							m.setFont(rs.getString("font"));
							m.setSendTime(rs.getString("Times"));
							m.setSize(rs.getInt("size"));
							m.setCol(rs.getString("color"));
							m.setCon(rs.getString(4));

							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);

						}

					}
				} else if (m.getMesType().equals(
						MessageType.message_hasfriendask)) {

					String sql1;
					String sql = "select *from AddFriend where  FriendId='"
							+ m.getSender() + "'";
					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];

						rs.first();
						int i = 0;
						str[i] = "";
						str[i] += rs.getString(1) + "  " + rs.getString(2)
								+ "  " + rs.getString(3) + "  ";
						sql1 = "select * from Login where UserId='"
								+ rs.getString(1) + "'";
						rs2 = link.doSelect(sql1);
						Message m2 = new Message();

						if (rs2.next()) {
							m2.setFriname(rs2.getString("name") + "("
									+ rs.getString(1) + ")");
						}
						m2.setMesType(MessageType.message_hasfriendask);
						m2.setSender(rs.getString(1));
						m2.setGetter(rs.getString(2));
						m2.setCon(rs.getString(3));
						System.out.println(m.getSender());
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m2);
						i++;
						while (rs.next()) {
							str[i] = "";
							str[i] += rs.getString(1) + "  " + rs.getString(2)
									+ "  " + rs.getString(3) + "  ";

							sql1 = "select * from Login where UserId='"
									+ rs.getString(1) + "'";
							rs2 = link.doSelect(sql1);
							m2.setMesType(MessageType.message_hasfriendask);
							if (rs2.next()) {
								m2.setFriname(rs2.getString("name") + "("
										+ rs.getString(1) + ")");
							}
							m2.setSender(rs.getString(1));
							m2.setGetter(rs.getString(2));
							m2.setCon(rs.getString(3));
							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m2);
							i++;
						}

					}
					sql = "DELETE from AddFriend where  FriendId='"
							+ m.getSender() + "'";
					link.Insert(sql);
				} else if (m.getMesType().equals(MessageType.message_sendfile)) {
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m);
				}
				// //////////////////////////个人资料消息
				else if (m.getMesType().equals(MessageType.message_data)) {
					String sql = "select * from Login where UserId='"
							+ m.getSender() + "'";
					rs = link.doSelect(sql);
					if (rs.next()) {
						String str = rs.getString("UserId") + ";"
								+ rs.getString("name") + ";"
								+ rs.getString("sex") + ";"
								+ rs.getString("age") + ";"
								+ rs.getString("brithday") + ";"
								+ rs.getString("shengxiao") + ";"
								+ rs.getString("xinzuo") + ";"
								+ rs.getString("xuexing") + ";"
								+ rs.getString("job") + ";"
								+ rs.getString("xueli") + ";"
								+ rs.getString("school") + ";"
								+ rs.getString("telphone") + ";"
								+ rs.getString("youxiang") + ";"
								+ rs.getString("zuye") + ";"
								+ rs.getString("guxiang") + ";"
								+ rs.getString("place") + ";"
								+ rs.getString("youbian") + ";"
								+ rs.getString("personsign") + ";";
						m.setCon(str);
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
					}
				}
				// //////////////////////////
				else if (m.getMesType().equals(MessageType.message_fridata)) {
					String sql = "select * from Login where UserId='"
							+ m.getSender() + "'";
					rs = link.doSelect(sql);
					if (rs.next()) {
						String str = rs.getString("UserId") + ";"
								+ rs.getString("name") + ";"
								+ rs.getString("sex") + ";"
								+ rs.getString("age") + ";"
								+ rs.getString("brithday") + ";"
								+ rs.getString("shengxiao") + ";"
								+ rs.getString("xinzuo") + ";"
								+ rs.getString("xuexing") + ";"
								+ rs.getString("job") + ";"
								+ rs.getString("xueli") + ";"
								+ rs.getString("school") + ";"
								+ rs.getString("telphone") + ";"
								+ rs.getString("youxiang") + ";"
								+ rs.getString("zuye") + ";"
								+ rs.getString("guxiang") + ";"
								+ rs.getString("place") + ";"
								+ rs.getString("youbian") + ";"
								+ rs.getString("personsign") + ";";
						m.setCon(str);
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
					}
				} else if (m.getMesType().equals(MessageType.message_ret_ip)) {

					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_ip);
					m2.setAddress(ManageClientThread.getClientip(m.getSender()));
					System.out.println(m2.getAddress().toString());
					m2.setGetter(m.getGetter());
					m2.setSender(m.getSender());
					m2.setFriendport(m.getMyport());
					m2.setMyport(m.getFriendport());
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m2);

				}

				else if (m.getMesType().equals(MessageType.message_acceptvdo)) {

					Message m2 = new Message();
					m2.setMesType(MessageType.message_acceptvdo);
					m2.setAddress(ManageClientThread.getClientip(m.getGetter()));

					m2.setGetter(m.getGetter());
					m2.setSender(m.getSender());
					m2.setFriendport(m.getFriendport());
					m2.setMyport(m.getMyport());
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m2);

				} else if (m.getMesType().equals(MessageType.message_jujuevdo)) {

					Message m2 = new Message();
					m2.setMesType(MessageType.message_jujuevdo);
					m2.setAddress(ManageClientThread.getClientip(m.getGetter()));

					m2.setGetter(m.getGetter());
					m2.setSender(m.getSender());
					m2.setFriendport(m.getFriendport());
					m2.setMyport(m.getMyport());
					SerConClientThread sc = ManageClientThread
							.getClientThread(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(
							sc.s.getOutputStream());
					oos.writeObject(m2);

				}
				// //////////////////////////保存个人资料
				else if (m.getMesType().equals(MessageType.message_savedata)) {

					String[] strs = m.getCon().split(";");
					// /////////////做个人资料的更新
					String sql = "Update  Login set name='" + strs[1] + "', "
							+ "sex='" + strs[2] + "', " + "age=" + strs[3]
							+ ", " + "brithday='" + strs[4] + "', "
							+ "shengxiao='" + strs[5] + "', " + "xinzuo='"
							+ strs[6] + "', " + "xuexing='" + strs[7] + "', "
							+ "job='" + strs[8] + "', " + "xueli='" + strs[9]
							+ "', " + "school='" + strs[10] + "', "
							+ "telphone='" + strs[11] + "', " + "youxiang='"
							+ strs[12] + "', " + "zuye='" + strs[13] + "', "
							+ "guxiang='" + strs[14] + "', " + "place='"
							+ strs[15] + "', " + "youbian='" + strs[16] + "' "
							+ "where UserId='" + m.getSender() + "'";
					rs = link.query(sql);
				}
				// //////////////////////////
				else if (m.getMesType().equals(MessageType.message_Qun_mes)) {
					String sql = "select UserId from [" + m.getGetter()
							+ "]where UserId!=" + m.getSender();
					rs = link.doSelect(sql);
					sql = " insert into QunChatContent(QunId,UserId,Content,Times,font,size,color) values('"
							+ m.getGetter()
							+ "','"
							+ m.getSender()
							+ "','"
							+ m.getCon()
							+ "','"
							+ m.getSendTime()
							+ "','"
							+ m.getFont()
							+ "','"
							+ m.getSize()
							+ "','"
							+ m.getCol() + "')";
					link.Insert(sql);
					while (rs.next()) {
						m.setSender(Integer.toString(rs.getInt("UserId")));
						System.out
								.println("m.getSender()     " + m.getSender());
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						if (sc != null) {
							ObjectOutputStream oos = new ObjectOutputStream(
									sc.s.getOutputStream());
							oos.writeObject(m);
						}
					}

				} else if (m.getMesType()
						.equals(MessageType.message_Qun_oldmes)) {
					System.out.println("        fffffff");
					String sql = "select * from QunChatContent where QunId="
							+ m.getGetter();

					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];
						rs.first();
						m.setFont(rs.getString("font"));
						m.setSendTime(rs.getString("Times"));
						m.setSize(rs.getInt("size"));
						m.setCol(rs.getString("color"));
						m.setCon(rs.getString("Content"));
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
						while (rs.next()) {
							m.setFont(rs.getString("font"));
							m.setSendTime(rs.getString("Times"));
							m.setSize(rs.getInt("size"));
							m.setCol(rs.getString("color"));
							m.setCon(rs.getString("Content"));
							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);
						}
					}
				} else if (m.getMesType().equals(MessageType.message_Qunfile)) {
					String sql = "select * from QunChatContent where QunId="
							+ m.getGetter();

					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {
						String str[] = new String[count];
						rs.first();
						m.setGetter(rs.getString("UserId"));
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
						while (rs.next()) {
							m.setGetter(rs.getString("UserId"));
							m.setMesType(MessageType.message_sendfile);
							sc = ManageClientThread.getClientThread(m
									.getGetter());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);
						}
					}
				} else if (m.getMesType().equals(
						MessageType.message_getQunPeople)) {
					System.out.println("   " + m.getGetter() + "  "
							+ m.getSender());
					String sql = "select UserId from [" + m.getGetter()
							+ "]where UserId!=" + m.getSender();
					rs = link.doSelect(sql);
					int count = 0;
					while (rs.next()) {
						count++;
					}
					if (count != 0) {

						String str[] = new String[count];
						rs.first();
						System.out.println("" + rs.getInt(1));
						m.setCon("" + rs.getInt("UserId"));
						SerConClientThread sc = ManageClientThread
								.getClientThread(m.getSender());
						ObjectOutputStream oos = new ObjectOutputStream(
								sc.s.getOutputStream());
						oos.writeObject(m);
						while (rs.next()) {
							System.out.println("" + rs.getInt(1));
							m.setCon("" + rs.getInt("UserId"));
							m.setMesType(MessageType.message_getQunPeople);
							sc = ManageClientThread.getClientThread(m
									.getSender());
							oos = new ObjectOutputStream(sc.s.getOutputStream());
							oos.writeObject(m);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}

	}
}
