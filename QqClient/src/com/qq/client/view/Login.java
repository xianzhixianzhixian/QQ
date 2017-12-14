/**
 * ����:qq�ͻ��˵�¼����
 */
package com.qq.client.view;

import com.qq.common.*;
import com.qq.client.tools.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.qq.client.model.QqClientUser;
import com.qq.common.User;

import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener,MouseListener,KeyListener{
	private boolean isDragged = false;
	private Point loc = null;  
	private JButton button;
    private Point tmp = null;  
    private JPasswordField passwordField;
    private JTextField textField;
    JLabel label;
	public Login() {
		this.addKeyListener(this);
		this.addMouseListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		button = new JButton(new ImageIcon("image/loginbutton.png"));
		button.setBounds(122, 299, 194, 29);
		button.addActionListener(this);
		getContentPane().add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, 239, 194, 29);
		getContentPane().add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(122, 192, 194, 29);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JCheckBox checkBox = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
		checkBox.setBounds(122, 270, 103, 23);
		getContentPane().add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("\u81EA\u52A8\u767B\u5F55");
		checkBox_1.setBounds(235, 270, 103, 23);
		getContentPane().add(checkBox_1);
		
		JButton btnNewButton = new JButton(new ImageIcon("image/min.jpg"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);			
			}
		});
		btnNewButton.setBounds(376, 0, 28, 29);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(new ImageIcon("image/exit.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(402, 0, 28, 29);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("image/login.jpg"));
		lblNewLabel.setBounds(0, 0, 430, 182);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("image/touxiang.png"));
		lblNewLabel_1.setBounds(24, 192, 82, 78);
		getContentPane().add(lblNewLabel_1);
		
		label = new JLabel("ע���˺�");
		label.setBounds(326, 199, 78, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u627E\u56DE\u5BC6\u7801");
		label_1.setBounds(326, 246, 56, 15);
		getContentPane().add(label_1);
		this.setSize(430, 350);
		this.setUndecorated(true);
		
		this.setVisible(true);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 510) / 2;  

        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 290) / 2;  
        
        // Ϊ�����������¼�  
        this.addMouseListener(new java.awt.event.MouseAdapter() {  

            public void mouseReleased(MouseEvent e) {  

                isDragged = false;  

                // Ϊָ���Ĺ�����ù��ͼ��  

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  

            }  

            public void mousePressed(MouseEvent e) {  

                tmp = new Point(e.getX(), e.getY());  

                isDragged = true;  

                setCursor(new Cursor(Cursor.MOVE_CURSOR));  

            }
        });  

   

        this.addMouseMotionListener(new MouseMotionAdapter() {  

            // ��갴��������ϰ��²��϶�ʱ���á�  

            public void mouseDragged(MouseEvent e) {  
            	
                if (isDragged) {  

                    loc = new Point(getLocation().x + e.getX() - tmp.x,  

                            getLocation().y + e.getY() - tmp.y);  

                    setLocation(loc);  

                }  

            }  

        });
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login login = new Login();
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
	
		//����û������¼
		if(arg0.getSource()== button )
		{
			QqClientUser qqClientUser=new QqClientUser();
			User u=new User();
			u.setUserId(textField.getText().trim());
			u.setPasswd(new String(passwordField.getPassword()));
			u.setUnload("2");
			u.setRegiste("1");
			InetAddress address = null;
			try {
				
				address=InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			
			u.setAddress(address);
			Message ms = qqClientUser.checkUser(u);
			if(ms.getMesType().equals("1"))
			{
				try {
					
					//�Ѵ��������б�������ǰ.
					ms.setMesType(MessageType.message_ret_onLineFriend);
					QqFriendList qqList=new QqFriendList(u.getUserId(),ms);
					ManageQqFriendList.addQqFriendList(u.getUserId(), qqList);
					
					//����һ��Ҫ�󷵻����ߺ��ѵ������.
					ObjectOutputStream oos=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
					
					//��һ��Message
					Message m=new Message();
					m.setMesType(MessageType.message_get_onLineFriend);
					//ָ����Ҫ�������qq�ŵĺ������.
					m.setSender(u.getUserId());
					oos.writeObject(m);
				
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

				//�رյ���¼����
				this.dispose();
			}else if(ms.getMesType().equals("3"))
			{
				JOptionPane.showMessageDialog(this,"���û��ѵ�¼");
			}
			else{
				JOptionPane.showMessageDialog(this,"�û����������");
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if((e.getPoint().getX()<326+78)&&(e.getPoint().getX()>326)&&(e.getPoint().getY()<199+15)&&(e.getPoint().getY()>199))
		{			
			label.setForeground(Color.blue);
			new Register();
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}


}