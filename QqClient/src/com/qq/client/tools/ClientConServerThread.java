/**
 * ���ǿͻ��˺ͷ������˱���ͨѶ���߳�.
 */
package com.qq.client.tools;

import com.qq.client.view.*;

import java.awt.Component;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.*;
public class ClientConServerThread extends Thread {

	private static final Component NULL = null;
	private Socket s;
	ReceveFrame receveframe;
	MessaeFrame ms=new MessaeFrame("1");
	//���캯��
	public ClientConServerThread(Socket s)
	{
		this.s=s;
		ms.setVisible(false);
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			//��ͣ�Ķ�ȡ�ӷ������˷�������Ϣ
			try {
				
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				
				if(m.getMesType().equals(MessageType.message_comm_mes))
				{
				
					//�Ѵӷ����������Ϣ����ʾ������ʾ���������
					QqChat qqChat=ManageQqChat.getQqChat(m.getGetter()+" "+m.getSender());
					//��ʾ
					if(qqChat!=null)
						qqChat.showMessage(m);
						if(qqChat==null)
						{
							if(m!=null)
							{	
								ms.setMs(m);
								ms.addJLabel(m.getGetter(),m.getSender());	
								ms.setVisible(true);						
							}
						}

				}else if(m.getMesType().equals(MessageType.message_ret_onLineFriend)){
					
					String con=m.getCon();
					String friends[]=con.split(" ");
					String getter=m.getGetter();
					//�޸���Ӧ�ĺ����б�.
					QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(getter);
					
					//�������ߺ���.
					if(qqFriendList!=null)
					{
						qqFriendList.upateFriend(m);
					}
				}else if(m.getMesType().equals(MessageType.messgae_ret_unloadFriends)){
					
					String con=m.getCon();
					String friends[]=con.split(" ");
					String getter=m.getGetter();
					//�޸���Ӧ�ĺ����б�.
					QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(getter);
					
					//�������ߺ���.
					if(qqFriendList!=null){
						
						qqFriendList.upateunloadFriend(m);;
					}
					
				}else if(m.getMesType().equals(MessageType.message_ret_addFriend)){
					
					String con=m.getCon();
					if(con == null){	
						
						JOptionPane.showMessageDialog(NULL, "û�и��û�");
					}
			
				}else if(m.getMesType().equals(MessageType.message_acceptadd)){
					
					QqFriendList qqList = ManageQqFriendList.getQqFriendList(m.getGetter());
					if(qqList!=null){
						qqList.init_update(m);
					}
				}else if(m.getMesType().equals(MessageType.message_tiren)){
					
					QqFriendList qqList = ManageQqFriendList.getQqFriendList(m.getGetter());
					JOptionPane.showMessageDialog(NULL, m.getGetter()+" ��������������");
					
					if(qqList!=null){
						
						qqList.dispose();
					}
					
					ManageClientConServerThread.getClientConServerThread(m.getGetter()).stop();
					ManageClientConServerThread.RemoveClientConServerThread(m.getGetter());
				
				}else if(m.getMesType().equals(MessageType.message_close)){
					
					QqFriendList qqList = ManageQqFriendList.getQqFriendList(m.getGetter());
					JOptionPane.showMessageDialog(qqList, " �Բ��𣬷�������ʱ�ر�");	
					qqList.dispose();
					ManageClientConServerThread.getClientConServerThread(m.getGetter()).stop();
					ManageClientConServerThread.RemoveClientConServerThread(m.getGetter());
					//ManageClientConServerThread.getClientConServerThread(m.getGetter()).s.close();
				}else if(m.getMesType().equals(MessageType.message_jujueadd)){
					
					JOptionPane.showMessageDialog(NULL, m.getSender()+"�ܾ�����ĺ�������");
				
				}else if(m.getMesType().equals(MessageType.message_gonggao)){
					
					//������ͬ��Ļ�ִ������һ�䣬������ͬ����Ϣ��
					new Gonggao(m);
				
				}else if(m.getMesType().equals(MessageType.message_ret_oldMessage)){
				
					//�Ѵӷ����������Ϣ����ʾ������ʾ���������
					QqChat qqChat=ManageQqChat.getQqChat(m.getSender()+" "+m.getGetter());
					//��ʾ
					qqChat.showoldMessage(m);
					
				}else if(m.getMesType().equals(MessageType.message_ret_outlineMessage)){
					
					QqChat qqChat=ManageQqChat.getQqChat(m.getSender()+" "+m.getGetter());
					//��ʾ
					qqChat.showMessage(m);
					
				}else if(m.getMesType().equals(MessageType.message_ret_pointMessage)){
					
					if(m!=null){
					
						//QqChat qqChat=ManageQqChat.getQqChat("sfs");					
						//��ʾ	
						ms.setMs(m);
						ms.setVisible(true);
						ms.addJLabel(m.getSender(),m.getGetter());
					}
					
				}else if(m.getMesType().equals(MessageType.message_addpoint)){
					
					if(m!=null){
					
						//QqChat qqChat=ManageQqChat.getQqChat("sfs");					
						//��ʾ	
						System.out.println("m.setFriname "+m.getFriname());
						ms.setVisible(true);
						ms.setMs(m);
						ms.addJLabel(m.getGetter(),m.getSender());
					}
					
				}else if(m.getMesType().equals(MessageType.message_hasfriendask)){
					
					if(m!=null){
						
						//QqChat qqChat=ManageQqChat.getQqChat("sfs");					
						//��ʾ	
						ms.setVisible(true);
						ms.setMs(m);
						ms.addJLabel(m.getGetter(),m.getSender());
					}
					
				}else if(m.getMesType().equals(MessageType.message_shake)){
					
					QqChat qqChat=ManageQqChat.getQqChat(m.getGetter()+" "+m.getSender());
					//��ʾ
					if(qqChat!=null)
						qqChat.shake(m);
					
				}else if(m.getMesType().equals(MessageType.message_sendfile)){
					
					if(receveframe ==null){
						
						receveframe = new ReceveFrame ();
						receveframe.setMs(m);
						receveframe.setVisible(true);
						
					}else{
						
						receveframe.setMs(m);
						receveframe.saveFile();						
						receveframe.setVisible(false);
						receveframe=null;
					}
				}else if(m.getMesType().equals(MessageType.message_data)){
					
					//���ظ�������
					PersonalInfo personalinfo=new PersonalInfo(m.getSender());
					personalinfo.setTexts(m.getCon());
					personalinfo.setVisible(true);
				}else if(m.getMesType().equals(MessageType.message_fridata)){
					
					friendPersonalInfo personalinfo=new friendPersonalInfo(m.getSender());
					personalinfo.setTexts(m.getCon());
					personalinfo.setVisible(true);
					
				}else if(m.getMesType().equals(MessageType.message_ret_ip)){
					
					//������ͬ��Ļ�ִ������һ�䣬������ͬ����Ϣ��
					AccepVideo accpvideo=new AccepVideo(m);
				
				}else if(m.getMesType().equals(MessageType.message_acceptvdo)){
					
					//������ͬ��Ļ�ִ������һ�䣬������ͬ����Ϣ��
					new VAplay(m.getMyport(),m.getFriendport(),m.getAddress());
				
				}else if(m.getMesType().equals(MessageType.message_jujuevdo)){
					
					//������ͬ��Ļ�ִ������һ�䣬������ͬ����Ϣ��
					JOptionPane.showMessageDialog(NULL, m.getGetter()+"�ܾ��������Ƶ����");
				
				}else if (m.getMesType().equals(MessageType.message_Qun_mes)) {
					
					QunChat qunChat = ManageQqChat.getQunChat(m.getSender()+"  "
							+ m.getGetter());
					
					if (qunChat != null){
						System.out.println(m.getSender()+"  "+m.getGetter());
						qunChat.showMessage(m);
					}
						
					if (qunChat == null) {
						
						if (m != null) {
							ms.setMs(m);
							ms.addJLabel(m.getGetter(), m.getSender());
							ms.setVisible(true);
						}
					}
				}else if (m.getMesType().equals(MessageType.message_Qun_oldmes)){
					
					QunChat qunChat = ManageQqChat.getQunChat(m.getSender()+"  "+m.getGetter());
					
					// ��ʾ
                    if(qunChat==null)  
                    	System.out.println("            lllll");
					
                    qunChat.showoldMessage(m);
				}else if (m.getMesType().equals(MessageType.message_getQunPeople)){
					
					QunChat qunChat = ManageQqChat.getQunChat(m.getSender()+"  "+m.getGetter());
					qunChat.addJLabel(m.getGetter(), m.getCon());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
	
}
