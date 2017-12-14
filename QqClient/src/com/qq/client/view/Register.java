package com.qq.client.view;

import com.qq.common.*;
import com.qq.client.tools.*;

import java.io.*;

import javax.swing.*;

import com.qq.client.model.QqClientUser;
import com.qq.common.User;

import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
public class Register extends JFrame implements ActionListener{
	private JLabel r_label1,r_label2,r_yanzhengma,r_label3;
	private JButton r_jb1,r_jb2,r_jb3;
	private JTextField textField;
	private JTextField textField_1;
	private JRadioButton r_male,r_female;
	private JComboBox r_cbcity,r_cbsheng,r_cbnation,r_cbmonth,r_cbmdate,r_cbyear,r_cb1;
	private boolean isDragged = false;
	private Point loc = null;  
    private Point tmp = null;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	Register register = new Register();
		

	}
	public Register(){
		
		this.setSize(790,570);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 790, 640);
		getContentPane().add(panel);
		r_jb2 = new JButton(new ImageIcon("image/register/fexit.png"));
		r_jb2.setBounds(760, 0, 30, 30);
		r_jb2.addActionListener(this);
		panel.setLayout(null);
		panel.add(r_jb2);
		
		r_jb3 = new JButton(new ImageIcon("image/register/fmin.png"));
		r_jb3.setBounds(730, 0, 30, 30);
		r_jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
		});
		panel.add(r_jb3);
		
		JLabel r_label1 = new JLabel(new ImageIcon("image/register/Register.jpg"));
		r_label1.setBounds(0, 0, 790, 91);
		panel.add(r_label1);
		
		JLabel r_label2 = new JLabel(new ImageIcon("image/register/QQ.jpg"));
		r_label2.setBounds(0, 91, 206, 76);
		panel.add(r_label2);
		r_jb1 = new JButton(new ImageIcon("image/register/r.jpg"));
		r_jb1.setBounds(297, 516, 174, 46);
		panel.add(r_jb1);
		r_jb1.addActionListener(this);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("�����Ķ���ͬ����ط����������˽����");
		chckbxNewCheckBox.setBounds(269, 486, 262, 23);
		panel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxqq = new JCheckBox("\u540C\u65F6\u5F00\u901AQQ\u7A7A\u95F4");
		chckbxqq.setBounds(269, 461, 135, 23);
		panel.add(chckbxqq);
		
		textField = new JTextField();
		textField.setBounds(273, 425, 110, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel r_yanzhengma = new JLabel("New label");
		r_yanzhengma.setBounds(386, 424, 110, 30);
		panel.add(r_yanzhengma);
		
		textField_1 = new JTextField();
		textField_1.setBounds(269, 204, 227, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		r_male = new JRadioButton("��");
		r_male.setBounds(269, 332, 45, 23);
		panel.add(r_male);
		r_female = new JRadioButton("Ů");
		r_female.setBounds(406, 332, 45, 23);
		panel.add(r_female);
		ButtonGroup bg = new ButtonGroup();	
		bg.add(r_male);	
		bg.add(r_female);
		r_cbyear = new JComboBox();
		r_cbyear.setBounds(269, 361, 77, 23);
		r_cbyear.setModel(new DefaultComboBoxModel(new String[] {"\u5E74", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"}));
		panel.add(r_cbyear);
		
		r_cbmonth = new JComboBox();
		r_cbmonth.setModel(new DefaultComboBoxModel(new String[] {"��", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		r_cbmonth.setBounds(360, 361, 44, 23);
		panel.add(r_cbmonth);
		
		r_cbmdate = new JComboBox();
		r_cbmdate.setModel(new DefaultComboBoxModel(new String[] {"��", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"}));
		
		r_cbmdate.setBounds(416, 361, 45, 23);
		panel.add(r_cbmdate);
		
		JComboBox r_cbnation = new JComboBox();
		r_cbnation.setBounds(269, 394, 75, 21);
		panel.add(r_cbnation);
		
		JComboBox r_cbsheng = new JComboBox();
		r_cbsheng.setBounds(344, 394, 75, 21);
		panel.add(r_cbsheng);
		
		JComboBox r_cbcity = new JComboBox();
		r_cbcity.setBounds(419, 394, 77, 21);
		panel.add(r_cbcity);
		
		JLabel label = new JLabel("\u6635\u79F0:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(182, 205, 77, 18);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(205, 250, 54, 15);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(184, 296, 75, 15);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u6027\u522B:");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(205, 336, 54, 15);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("\u51FA\u751F\u65E5\u671F:");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(184, 365, 75, 15);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u6240\u5728\u5730:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(184, 397, 75, 15);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("\u9A8C\u8BC1\u7801:");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(205, 432, 54, 15);
		panel.add(label_6);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("image/register/zhuce.png"));
		lblNewLabel.setBounds(228, 147, 606, 42);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("image/register/white.png"));
		lblNewLabel_1.setBounds(0, 0, 790, 612);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(269, 248, 227, 18);
		panel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(269, 294, 227, 18);
		panel.add(passwordField_1);
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

            	if(e.getY()<30)
            	{
                tmp = new Point(e.getX(), e.getY());  

                isDragged = true;  

                setCursor(new Cursor(Cursor.MOVE_CURSOR));  

            }  
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
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		
		if(e.getSource()==r_jb2)
		{
			this.dispose();	
		}
		if(e.getSource()==r_jb1)
		{
			
			if(textField_1.getText().toString() == null)
			{
				JOptionPane.showMessageDialog(this,"����д�ǳ�");
			}
			else if((String.valueOf(passwordField.getPassword()).isEmpty())||(String.valueOf(passwordField_1.getPassword()).isEmpty()))
			{
				JOptionPane.showMessageDialog(this,"����д����");
			}
			else if(!((String.valueOf(passwordField.getPassword()).equals((String.valueOf(passwordField_1.getPassword()))))))
			{
				JOptionPane.showMessageDialog(this,"�������벻һ��");
			}
			else if(r_cbyear.getSelectedItem().toString().equals("��"))
			{
				JOptionPane.showMessageDialog(this,"��ѡ�����");
			}
			else if(r_cbmonth.getSelectedItem().toString().equals("��"))
			{
				JOptionPane.showMessageDialog(this,"��ѡ���·�");
			}
			else if(r_cbmdate.getSelectedItem().toString().equals("��"))
			{
				JOptionPane.showMessageDialog(this,"��ѡ���շ�");
			}else 
			{
				String mes = "";
				if(r_male.isSelected())
					mes = mes + textField_1.getText().toString() +" " + String.valueOf(passwordField.getPassword())
						+" �� "+r_cbyear.getSelectedItem().toString()+"-"+r_cbmonth.getSelectedItem().toString()
						+"-"+r_cbmdate.getSelectedItem().toString();
				else
					mes = mes + textField_1.getText().toString() +" " + String.valueOf(passwordField.getPassword())
					+" Ů "+r_cbyear.getSelectedItem().toString()+"-"+r_cbmonth.getSelectedItem().toString()
					+"-"+r_cbmdate.getSelectedItem().toString();
				
				QqClientUser qqClientUser=new QqClientUser();
				User u=new User();
				u.setUserId("1");
				u.setUnload("1");
				u.setPasswd("2");
				u.setRegiste(mes);
				Message m= qqClientUser.Registe(u);
				JOptionPane.showMessageDialog(this,"��ע����ʺ��� "+m.getCon()+" ���μ�");
				this.dispose();
			}

		}
	}
	
}
