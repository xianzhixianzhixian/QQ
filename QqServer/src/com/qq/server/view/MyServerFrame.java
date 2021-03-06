/**
 * 这是服务器端的控制界面，可以完成启动服务器，关闭服务器
 * 可以管理和监控用户.
 */
package com.qq.server.view;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.server.model.LinkClass;
import com.qq.server.model.ManageClientThread;
import com.qq.server.model.MyQqServer;
import com.qq.server.model.SerConClientThread;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class MyServerFrame extends JFrame implements ActionListener ,MouseListener,WindowListener{

	
	JPanel jp1;
	JButton jb1,jb2,daochu,tiren,gonggao;
	public JTextArea textArea,textArea_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,lblNewLabel_4;
	JTree jtree; 
	DefaultMutableTreeNode root,liebiao; 
	DefaultTreeModel treeModel=null;
	LinkClass link = new LinkClass();
	ResultSet rs = null;
	DateFormat ddtf = DateFormat.getDateTimeInstance();
	private JLabel label_2;
	private JLabel label_3;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyServerFrame mysf=new MyServerFrame();
	}
	
	public MyServerFrame()
	{
		jp1=new JPanel();
		jb1=new JButton("启动服务器");
		jb1.setBounds(138, 556, 120, 23);
		jb1.addActionListener(this);
		jb2=new JButton("关闭服务器");
		jb2.setBounds(301, 556, 120, 23);
		jb2.addActionListener(this);
		jp1.setLayout(null);
		jp1.add(jb1);
		jp1.add(jb2);
		
		
		getContentPane().add(jp1);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 43, 374, 376);
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(10, 43, 374, 376);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jp1.add(scrollPane_1);
		textArea.setText("日志记录：\n");
	
		daochu = new JButton("\u5BFC\u51FA\u65E5\u5FD7");
		daochu.setBounds(290, 10, 93, 23);
		daochu.addActionListener(this);
		jp1.add(daochu);
		
		
		
		root=new DefaultMutableTreeNode("在线用户:"); 
		jtree=new JTree(root);
		jtree.setBounds(407, 43, 152, 376);
		
		jtree.setEditable(true); 		
		jtree.addMouseListener(this);
		treeModel=(DefaultTreeModel)jtree.getModel(); 
		JScrollPane scrollPane=new JScrollPane(); 
		scrollPane.setViewportView(jtree); 
		scrollPane.setBounds(407, 43, 152, 376);
		jp1.add(scrollPane);
		
		tiren = new JButton("\u8E22\u4EBA");
		tiren.setBounds(492, 10, 67, 23);
		tiren.addActionListener(this);
		jp1.add(tiren);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(395, 0, 2, 425);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.GRAY);
		jp1.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(5, 425, 566, 2);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.GRAY);
		jp1.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setBounds(5, 0, 2, 550);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(Color.GRAY);
		jp1.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel();
		lblNewLabel_3.setBounds(569, 0, 2, 550);
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(Color.GRAY);
		jp1.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel();
		lblNewLabel_4.setBounds(5, 0, 566, 2);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(Color.GRAY);
		jp1.add(lblNewLabel_4);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 457, 463, 79);
		JScrollPane scrollPane_2 = new JScrollPane(textArea_1);
		scrollPane_2.setBounds(10, 457, 463, 79);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jp1.add(scrollPane_2);
		
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Color.GRAY);
		label.setBounds(5, 548, 566, 2);
		jp1.add(label);
		
		gonggao = new JButton("\u53D1\u9001\u516C\u544A");
		gonggao.setBounds(476, 457, 93, 79);
		gonggao.addActionListener(this);
		jp1.add(gonggao);
		
		JLabel label_1 = new JLabel("\u7CFB\u7EDF\u516C\u544A");
		label_1.setBounds(10, 429, 67, 28);
		jp1.add(label_1);
		
		label_2 = new JLabel("\u7CFB\u7EDF\u65E5\u5FD7");
		label_2.setBounds(15, 18, 54, 15);
		jp1.add(label_2);
		
		label_3 = new JLabel("\u5728\u7EBF\u7528\u6237");
		label_3.setBounds(407, 14, 72, 15);
		jp1.add(label_3);

		this.setResizable(false);
		this.setSize(585, 607);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if(arg0.getSource()==gonggao)
		{
			gonggao();
		}
		if(arg0.getSource()==jb1)
		{
			new MyQqServer(this);
		}
		if(arg0.getSource()==tiren)
		{
			deltree();
		}
		if(arg0.getSource()==jb2)
		{
			notifyOther();
		}
		if(arg0.getSource()==daochu)
		{
			FileWriter fw;
			BufferedWriter out;
			JFileChooser dig = new JFileChooser();
			int state = dig.showSaveDialog(this);
			if(state == JFileChooser.APPROVE_OPTION)
			{
				try{
					File dir = dig.getCurrentDirectory();
					String name = dig.getSelectedFile().getName();
					File file = new File(dir,name);
					fw = new FileWriter(file);
					out = new BufferedWriter(fw);
					out.write(textArea.getText());
					out.close();
					fw.close();
				}catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}

	public void autodeltree(String id)
	{
		DefaultMutableTreeNode selectNode;
		Enumeration<?> enumeration;
		enumeration = root.postorderEnumeration();
		while (enumeration.hasMoreElements()) { // 遍历枚举对象.
			// 先定义一个节点变量.
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) enumeration.nextElement();// 将节点名称给node.
			
			String str = node.toString(); 
			if(str.equals(id))
				{
					selectNode = node;
					DefaultTreeModel model=(DefaultTreeModel)jtree.getModel();
					//   通知它从其父节点中移除节点
					model.removeNodeFromParent(selectNode);
					
				}
		}

		//更新
		jtree.repaint();
	}
	
	public void deltree()
	{
		DefaultMutableTreeNode selectNode=(DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
		if(selectNode==null){
			return;
		}
		String str = selectNode.toString(); 
		DefaultTreeModel model=(DefaultTreeModel)jtree.getModel();
		//   通知它从其父节点中移除节点
		model.removeNodeFromParent(selectNode);
		//更新
		jtree.repaint();
		Message m = new Message();
		m.setGetter(str);
		m.setMesType(MessageType.message_tiren);
		SerConClientThread sc=ManageClientThread.getClientThread(str);
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(sc.s.getOutputStream());
			oos.writeObject(m);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String sql = "select *from Outline  where UserId='"
				+ str + "'";
		rs = link.doSelect(sql);
		try {
			if (rs.next()) {
				sql = "Update Outline set OutlineTime=getdate() where UserId='"
						+ str + "'";
				link.Insert(sql);
			} else {
				sql = "insert into Outline(UserId) values('"
						+ str + "')";
				link.Insert(sql);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		sc.notifyunloadOther(str);
		sc.stop();
		
		ManageClientThread.RemoveClientThread(str);
		ManageClientThread.RemoveClientip(str);
		String info = ddtf.format(new Date()) + "-->帐号："
				+ str + "被踢出服务器--\n";
		addstr(info);
		
	}
	
	public void addtree(String s)
	{
	//	liebiao = new DefaultMutableTreeNode(s); 
	//	root.add(liebiao);
		
		//获取选中节点  
        DefaultMutableTreeNode selectedNode  = root;  
        //如果节点为空，直接返回  
        if (selectedNode == null) return;  
        //获取该选中节点的父节点  
        DefaultMutableTreeNode parent  = root;  
        //如果父节点为空，直接返回  
        if (parent == null) return;  
        //创建一个新节点  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(s);  
        //获取选中节点的选中索引  
        int selectedIndex = parent.getIndex(selectedNode);  
        //在选中位置插入新节点  
        treeModel.insertNodeInto(newNode, parent, selectedIndex + 1);  
		
	}
	public void addstr(String s)
	{
		textArea.append("--------------------------------------------------------------------------------------\n");
		textArea.append(s);
		textArea.append("\n");
	}
	
	public void notifyOther()
	{
		
		root.removeAllChildren();
		//得到所有在线的人的线程
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setMesType(MessageType.message_close);
			//取出在线人的id
			String onLineUserId=it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
				String sql = "select *from Outline  where UserId='"
						+ onLineUserId + "'";
				rs = link.doSelect(sql);
				try {
					if (rs.next()) {
						sql = "Update Outline set OutlineTime=getdate() where UserId='"
								+ onLineUserId + "'";
						link.Insert(sql);
					} else {
						sql = "insert into Outline(UserId) values('"
								+ onLineUserId + "')";
						link.Insert(sql);
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				ManageClientThread.getClientThread(onLineUserId).notifyunloadOther(onLineUserId);
				ManageClientThread.getClientThread(onLineUserId).stop();
				
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
		}
		ManageClientThread.hm.clear();;
		ManageClientThread.hm2.clear();
		String info = ddtf.format(new Date()) + "-->关闭服务器\n";
		addstr(info);
	}
	
	public void gonggao()
	{
		
		//得到所有在线的人的线程
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setMesType(MessageType.message_gonggao);
			m.setCon(textArea_1.getText());
			
			//取出在线人的id
			String onLineUserId=it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
		}
		String info = ddtf.format(new Date()) + "-->发送系统公告\n"+textArea_1.getText()+"\n";
		addstr(info);
		textArea_1.setText("");
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowClosing(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowClosed(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowActivated(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO 自动生成的方法存根
		notifyOther();
	}
}
