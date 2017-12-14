/**
 * ����qq�����������ڼ������ȴ�ĳ��qq�ͻ��ˣ�������
 */
package com.qq.server.model;

import com.qq.common.*;
import com.qq.server.view.MyServerFrame;

import java.net.*;
import java.awt.Color;
import java.io.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MyQqServer implements Runnable {

	LinkClass link = new LinkClass();
	ResultSet rs = null;
	MyServerFrame sever;
	DateFormat ddtf = DateFormat.getDateTimeInstance();

	public MyQqServer(MyServerFrame sever) {
		
		this.sever = sever;
		this.sever.textArea.append(ddtf.format(new Date()) + "������������\n");
		Thread xx = new Thread(this);
		xx.start();

	}

	public void run() {
		// TODO �Զ����ɵķ������
		try {

			// ��9999����
			ServerSocket ss = new ServerSocket(9999);
			// ����,�ȴ�����
			while (true) {
				Socket s = ss.accept();

				// ���տͻ��˷�������Ϣ.
				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("���������յ��û�id:" + u.getUserId() + "  ����:"
						+ u.getPasswd());
				Message m = new Message();
				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());
				String sql = "select * from Login where UserId='"
						+ u.getUserId() + "'and PassWord='" + u.getPasswd()
						+ "'";
				rs = link.doSelect(sql);
				if (!u.getRegiste().equals("1")) {
					String Registemessage[] = u.getRegiste().split(" ");
					String sql1 = " insert into Login(name,PassWord,sex,brithday) values('"
							+ Registemessage[0]
							+ "','"
							+ Registemessage[1]
							+ "','"
							+ Registemessage[2]
							+ "','"
							+ Registemessage[3] + "')";
					link.Insert(sql1);
					sql1 = "select * from Login";
					ResultSet rs1 = link.doSelect(sql1);
					rs1.last();
					String ID = rs1.getString("UserId");
					m.setMesType("4");
					m.setCon(ID);
					oos.writeObject(m);
					String info = ddtf.format(new Date()) + "-->�ʺţ�" + ID
							+ "ע����--\n";
					this.sever.addstr(info);
					String sql_insert = "insert into friendlist(fqq,mqq,fenzu,fenzucount,name) values('"
							+ ID
							+ "','"
							+ ID
							+ "','����','1','"
							+ Registemessage[0] + "')";
					link.Insert(sql_insert);
				} else if (u.getUnload().equals("1234567")) {

					SerConClientThread scct = ManageClientThread
							.getClientThread(u.getUserId());

					sql = "select *from Outline  where UserId='"
							+ u.getUserId() + "'";
					rs = link.doSelect(sql);
					if (rs.next()) {
						sql = "Update Outline set OutlineTime=getdate() where UserId='"
								+ u.getUserId() + "'";
						link.Insert(sql);
					} else {
						sql = "insert into Outline(UserId) values('"
								+ u.getUserId() + "')";
						link.Insert(sql);
					}

					String info = ddtf.format(new Date()) + "-->�ʺţ�"
							+ u.getUserId() + "������--\n";
					this.sever.addstr(info);
					this.sever.autodeltree(u.getUserId());
					
					ManageClientThread.RemoveClientThread(u.getUserId());

					ManageClientThread.RemoveClientip(u.getUserId());

					scct.notifyunloadOther(u.getUserId());
					scct.stop();

				}

				else if (rs.next()) {
					
					if (ManageClientThread.getClientThread(u.getUserId()) != null) {
						
						m.setMesType("3");
						oos.writeObject(m);
					} else {
						// ����һ���ɹ���½����Ϣ��
						m.setMesType("1");
						String con = "";
						String search_friend = "select * from friendlist where mqq='"
								+ u.getUserId() + "'";
						rs = link.query(search_friend);
						try {
							while (rs.next()) {
								if (rs.getString("fqq").equals(u.getUserId())) {
									m.setMyname(rs.getString("name") + "("
											+ u.getUserId() + ")");
								}

								con = con + rs.getString("name") + "("
										+ rs.getString("fqq") + ")-"
										+ rs.getString("fenzu") + " ";
								m.setFenzucount(rs.getString("fenzucount"));
							}

							m.setCon(con);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						oos.writeObject(m);
						// ����͵���һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ.
						SerConClientThread scct = new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);

						InetAddress address = u.getAddress();
						ManageClientThread.addClientip(u.getUserId(), address);
						// this.sever.textPane;
						String info = ddtf.format(new Date()) + "-->�ʺţ�"
								+ u.getUserId() + "��¼��-->��¼ip "
								+ u.getAddress().getHostAddress().toString()
								+ "\n";
						this.sever.addstr(info);
						this.sever.addtree(u.getUserId());
						// ������ÿͻ���ͨ�ŵ��߳�.
						scct.start();

						// ��֪ͨ���������û�.
						scct.notifyOther(u.getUserId());
					}

				} else {
					m.setMesType("2");
					oos.writeObject(m);
					// �ر�Socket
					s.close();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {

		}

	}

}
