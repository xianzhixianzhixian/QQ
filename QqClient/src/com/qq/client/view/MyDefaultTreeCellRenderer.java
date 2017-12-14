/**
 * ���ν���
 */

package com.qq.client.view;

import java.awt.Component;
import com.qq.common.*;
import javax.swing.ImageIcon;  
import javax.swing.JOptionPane;
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeCellRenderer;  
 
public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer  
{
    private static final long   serialVersionUID    = 1L;  
    private Message m;
    private String own;
    String onLineFriend[] = null;
    
    //��д����DefaultTreeCellRenderer�ķ���   
    MyDefaultTreeCellRenderer(String own,Message m)
    {
    	this.m = m;
    	this.own = own;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus)  
    {  
  
        //ִ�и���ԭ�Ͳ���  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
                row, hasFocus);  
  
        setText(value.toString());  
        if (sel)  
        {  
            setForeground(getTextSelectionColor());  
        }  
        else  
        {  
            setForeground(getTextNonSelectionColor());  
        }  
          
        //�õ�ÿ���ڵ��TreeNode  
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;  
        
        //�õ�ÿ���ڵ��text  
        
        String str = node.toString();  
        if(str.length()>5)
        {
        	int x = str.indexOf("(");
        	str = str.substring(x+1, str.length()-1);
        }
        //�ж����ĸ��ı��Ľڵ����ö�Ӧ��ֵ����������ڵ㴫�����һ��ʵ��,����Ը���ʵ�������һ��������������ʾ��Ӧ��ͼ�꣩  
        onLineFriend=m.getCon().split(" ");
        
        if(m.getMesType() == MessageType.message_get_onLineFriend)
        {
        	
        	if (node.isLeaf()&&str.equals(this.own))  
        	{ // JOptionPane.showMessageDialog(this,str+" + "+own);
        		this.setIcon(new ImageIcon("image/qq.gif"));  
        	}
        	else if(node.isLeaf())
        	{
        		this.setIcon(new ImageIcon("image/qq.png"));
        		for(int i=0;i<onLineFriend.length;i++)
        		{					
        			if(str.equals(onLineFriend[i]))
        				this.setIcon(new ImageIcon("image/qq.gif")); 
        		}
        	  
        	}
        	else
        		this.setIcon(new ImageIcon("image/lie.png")); 
        }
        if(m.getMesType() == MessageType.messgae_ret_unloadFriends)
        {
        	String onunloadFriend[]=m.getCon1().split(" ");
        	if (node.isLeaf()&&str.equals(this.own))  
        	{  
        		this.setIcon(new ImageIcon("image/qq.gif"));  
        	}
        	else if(node.isLeaf())
        	{
        		this.setIcon(new ImageIcon("image/qq.png"));
        		for(int i=0;i<onLineFriend.length;i++)
        		{		
        			if(str.equals(onLineFriend[i]))
        				this.setIcon(new ImageIcon("image/qq.gif"));       				
        		}
        		
        		for(int i=0;i<onunloadFriend.length;i++)
        		{		 
        			if(str.equals(onunloadFriend[i]))
        				this.setIcon(new ImageIcon("image/qq.png"));        				
        		}
        	  
        	}
        	else
        		this.setIcon(new ImageIcon("image/lie.png")); 
        }
        return this;  
    }  
  
}  