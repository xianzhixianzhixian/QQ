/**
 * �ҵĺ����б�,(Ҳ����İ���ˣ�������)
 */
package com.qq.client.view;

import com.qq.client.model.QqClientUser;
import com.qq.client.tools.*;
import com.qq.common.Message;
import com.qq.common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class QqFriendList extends JFrame implements ActionListener,MouseListener,WindowListener{

	//�����һ�ſ�Ƭ.
	
	private static final String JLabel = null;
	JPanel jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3,jphy_addnewfriend;
	JScrollPane jsp1;
	String owner;
	//����ڶ��ſ�Ƭ(İ����).
	
	JPanel jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	JLabel jb1s;
	
	HashMap friendlist=new HashMap<String, JLabel>();
	//������JFrame���ó�CardLayout
	CardLayout cl;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	QqFriendList qqFriendList=new QqFriendList();
	}
	
	//�������ߵĺ������
	public void upateFriend(Message m)
	{
		JLabel lab;
		String onLineFriend[]=m.getCon().split(" ");
		JOptionPane.showMessageDialog(this,this.owner);
		for(int i=0;i<onLineFriend.length;i++)
		{			
		//	jb1s[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
		//	friendlist.get(onLineFriend[i]).
			lab = (JLabel)friendlist.get("s");
			lab.setEnabled(true);
		}
	}
	
	public void upateunloadFriend(Message m)
	{
		String onLineFriend[]=m.getCon().split(" ");
		JLabel lab;
		for(int i=0;i<onLineFriend.length;i++)
		{
			
			//jb1s[Integer.parseInt(onLineFriend[i])-1].setEnabled(false);
			lab = (JLabel)friendlist.get("s");
			lab.setEnabled(true);
		}
	}
	
	
	public void init_update(String ownerId,Message m)
	{
		JLabel jlab;
		jlab=new JLabel(m.getCon().toString()+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
		jlab.setEnabled(false);
		
		jlab.addMouseListener(this);
		friendlist.put(m.getCon().toString(),jlab);
		jphy2.add(jlab);
		jphy2.repaint();
	}
	
	public QqFriendList(String ownerId,Message m)
	{
		
		addWindowListener(this);
		this.owner=ownerId;
		//�����һ�ſ�Ƭ(��ʾ�����б�)
		jphy_jb1=new JButton("�ҵĺ���");
		jphy_jb2=new JButton("İ����");
		jphy_jb2.addActionListener(this);
		jphy_jb3=new JButton("������");
		jphy1=new JPanel(new BorderLayout());
		jphy_addnewfriend = new JButton("��Ӻ���");
		jphy_addnewfriend.addActionListener(this);
		String friend[]=m.getCon().split(" ");


		if(friend.length < 10)
			jphy2=new JPanel(new GridLayout(10,1,4,4));
		else
			jphy2=new JPanel(new GridLayout(friend.length,1,4,4));
		//��jphy2����ʼ������.
		
		
		for(int i=0;i<friend.length;i++)
		{
			jb1s=new JLabel(friend[i].toString()+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			jb1s.setEnabled(false);
			if(jb1s.getText().equals(ownerId))
			{
				jb1s.setEnabled(true);
			}
			jb1s.addMouseListener(this);
			friendlist.put(friend[i].toString(),jb1s);
			jphy2.add(jb1s);
			
			
		}
		
		jphy3=new JPanel(new GridLayout(3,1));
		//��������ť���뵽jphy3
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);
		jphy3.add(jphy_addnewfriend);
		
		jsp1=new JScrollPane(jphy2);
		
		
		//��jphy1,��ʼ��
		jphy1.add(jphy_jb1,"North");
		jphy1.add(jsp1,"Center");
		jphy1.add(jphy3,"South");
		
		
		//����ڶ��ſ�Ƭ
		
		
		jpmsr_jb1=new JButton("�ҵĺ���");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2=new JButton("İ����");
		jpmsr_jb3=new JButton("������");
		jpmsr1=new JPanel(new BorderLayout());
		//�ٶ���20��İ����
		jpmsr2=new JPanel(new GridLayout(20,1,4,4));
		
		//��jphy2����ʼ��20İ����.
		JLabel []jb1s2=new JLabel[20];
		
		for(int i=0;i<jb1s2.length;i++)
		{
			jb1s2[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			jpmsr2.add(jb1s2[i]);
		}
		
		jpmsr3=new JPanel(new GridLayout(2,1));
		//��������ť���뵽jphy3
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);
		
		
		jsp2=new JScrollPane(jpmsr2);
		
		
		//��jphy1,��ʼ��
		jpmsr1.add(jpmsr3,"North");
		jpmsr1.add(jsp2,"Center");
		jpmsr1.add(jpmsr_jb3,"South");
		
		
		cl=new CardLayout();
		this.setLayout(cl);
		this.add(jphy1,"1");
		this.add(jpmsr1,"2");
		//�ڴ�����ʾ�Լ��ı��.
		this.setTitle(ownerId);
		this.setSize(140, 400);
		this.setVisible(true);
		
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//��������İ���˰�ť������ʾ�ڶ��ſ�Ƭ
		if(arg0.getSource()==jphy_jb2)
		{
			cl.show(this.getContentPane(), "2");
		}else if(arg0.getSource()==jpmsr_jb1){
			cl.show(this.getContentPane(), "1");
		}
		
		if(arg0.getSource()==jphy_addnewfriend)
		{
			new NewFriend(this.owner);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//��Ӧ�û�˫�����¼������õ����ѵı��.
		if(arg0.getClickCount()==2)
		{
			//�õ��ú��ѵı��
			String friendNo=((JLabel)arg0.getSource()).getText();
			//System.out.println("��ϣ���� "+friendNo+" ����");
			QqChat qqChat=new QqChat(this.owner,friendNo);
			
			//�����������뵽������
			ManageQqChat.addQqChat(this.owner+" "+friendNo, qqChat);
			
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jl=(JLabel)arg0.getSource();
		jl.setForeground(Color.red);
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jl=(JLabel)arg0.getSource();
		jl.setForeground(Color.black);
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void windowClosing(WindowEvent e) {
		// TODO �Զ����ɵķ������
		QqClientUser qqClientUser=new QqClientUser();
		User u=new User();
		u.setUserId(this.owner);
		u.setUnload("1234567");
		u.setPasswd("2");
		qqClientUser.unload(u);
		
		
	/*	System.exit(0);*/
	}

	public void windowClosed(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void windowActivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}

}
