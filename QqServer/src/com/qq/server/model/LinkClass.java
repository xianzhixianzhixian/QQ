package com.qq.server.model;
import java.awt.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LinkClass {

    List list=new List();int i=0;
    Connection con = null;  
	PreparedStatement pstm =null; 							
	ResultSet rs =null;
	String s=null;
	String s1;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������	
	}
	public LinkClass()
	{
		
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		try {
			Class.forName(driver);
			
			con=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=Chat","sa","123456");
		}
			catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public ResultSet query(String sql){ 
		//��ѯ���ؽ�� 
		try{    
			pstm=con.prepareStatement(sql);
			rs=pstm.executeQuery();
		}catch(Exception e){   
			System.out.println(e.getMessage());   
		}
		return rs;  
		
	}
	public void Insert(String sql)
	{
		try{    
			pstm=con.prepareStatement(sql);
			pstm.executeUpdate();
		}catch(Exception e){   
			System.out.println(e.getMessage());   
		}
	}
	public List getAddress()
	{
		return list;
	}
	
	public ResultSet doSelect(String sql)
	{
		try {
			pstm=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=pstm.executeQuery();
			return rs;
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}finally{
		
		}
		
	return rs;
	}

	
		
	

}
