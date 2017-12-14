/**
 * ����Ⱥ����Ľ���
 * ��Ϊ�ͻ��ˣ�Ҫ���ڶ�ȡ��״̬��������ǰ�������һ���߳�
 */
package com.qq.client.view;

import com.qq.client.tools.*;
import com.qq.client.view.QqChat.PicInfo;
import com.qq.common.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;


public class QunChat extends JFrame implements ActionListener,WindowListener,MouseListener {

	private JButton cd_send,cd_close,cd_sendpic,cd_jietu,cd_yymessage,cd_diange,cd_dgninput,cd_zhendong;
	private JButton cd_biqoing,cd_word,cd_magic,cd_yuyin,cd_shipin,cd_transfile,cd_createteam,cd_yuancheng,cd_biaoqing;
	private JButton cd_sharedisplay,cd_yinyong;
	private JLabel cd_touxiang,cd_qianming,cd_nname;
	private JButton cd_exit,cd_min,cd_max,cd_searchjilu,cd_jiacu,cd_xiahuaxian,cd_color,cd_xieti;
	private JComboBox cd_ziti,cd_size;
	private boolean isDragged = false;
	private Point loc = null;  
	JPanel panel_1,panel_2,panel_3,panel_4;
    private Point tmp = null;
    CardLayout  Cardjilu;
    JTextPane textPane_1,textPane;
    JTextPane textPane_2 ;
    String ownerId;
	String QunId;
	private QunPicsJWindow picWindow;
	private List<PicInfo> myPicInfo = new LinkedList<PicInfo>();
	private List<PicInfo> receivdPicInfo = new LinkedList<PicInfo>();
	private List<PicInfo> receivdoldPicInfo = new LinkedList<PicInfo>();
	private StyledDocument docChat = null;
	private StyledDocument docMsg = null;
	private StyledDocument oldMsg = null;
	DataInputStream fis = null;
	////////////////////////////////////////////
	JTextPane k=new JTextPane();
	JPanel Qunpane=new JPanel();
	int x,y,width,higth=0,record=0;
	JLabel[] jla=new JLabel[20];
	///////////////////////////////////////////
	Color Text_color=new Color(255,255,255);
	private JScrollPane scrollPane_1,scrollPane_2;
    /**
     * @wbp.nonvisual location=597,227
     */
    private final JLabel label2 = new JLabel("New label2222222222");
    /**
     * @wbp.nonvisual location=597,327
     */
    private final JLabel label1 = new JLabel("New label11111111111111");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ChatDialog cd = new ChatDialog();
		QunChat a = new QunChat("sd","d");a.setVisible(true);
	}
	class PicInfo{
		/* ͼƬ��Ϣ*/
		int pos;
		String val;
		public PicInfo(int pos,String val){
			this.pos = pos;
			this.val = val;
		}
		public int getPos() {
			return pos;
		}
		public void setPos(int pos) {
			this.pos = pos;
		}
		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}
		
	}
	
	
	/**
	 * ����ͼƬ
	 * 
	 * @param icon
	 */
	public void insertSendPic(ImageIcon imgIc) {
		//jpMsg.setCaretPosition(docChat.getLength()); // ���ò���λ��
		textPane_1.insertIcon(imgIc); // ����ͼƬ
	//	System.out.print(imgIc.toString());
		//insert(new FontAttrib()); // ���������Ի���
	}
	
	public JButton getPicBtn(){
		return cd_biaoqing;
	}
	public QunChat (String ownerId,String QunId) {
		
		
		this.addWindowListener(this);
		Text_color = Color.BLACK;
		this.ownerId=ownerId;
		this.QunId=QunId;
		
		this.setSize(728,533);
		
		this.setUndecorated(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 729, 92);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cd_touxiang = new JLabel(new ImageIcon("image/dialogimage/huisetouxiang.png"));
		cd_touxiang.setBounds(10, 10, 42, 42);
		panel.add(cd_touxiang);
		
		cd_exit = new JButton(new ImageIcon ("image/dialogimage/dexit.jpg"));
		cd_exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		cd_exit.addActionListener(this);
		cd_exit.setBounds(698, 0, 30, 30);
		panel.add(cd_exit);
		
		cd_min = new JButton(new ImageIcon ("image/dialogimage/dmin.jpg"));
		cd_min.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		cd_min.setBounds(640, 0, 30, 30);
		panel.add(cd_min);
		
		cd_max = new JButton(new ImageIcon ("image/dialogimage/dmax.jpg"));
		cd_max.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.MAXIMIZED_BOTH);  

			}
		});
		
		cd_max.setBounds(669, 0, 30,30);
		panel.add(cd_max);
		
		cd_yuyin = new JButton(new ImageIcon("image/dialogimage/yuyin.png"));
		cd_yuyin.setBounds(10, 62, 36, 30);
		panel.add(cd_yuyin);
		
		cd_shipin = new JButton(new ImageIcon("image/dialogimage/shipin.png"));
		cd_shipin.setBounds(56, 62, 36, 30);
		cd_shipin.addActionListener(this);
		panel.add(cd_shipin);
		
		cd_transfile = new JButton(new ImageIcon("image/dialogimage/tranfile.jpg"));
		cd_transfile.setBounds(102, 62, 36, 30);
		cd_transfile.addActionListener(this);
		panel.add(cd_transfile);
		
		cd_createteam = new JButton(new ImageIcon("image/dialogimage/createteam.jpg"));
		cd_createteam.setBounds(148, 62, 36, 30);
		panel.add(cd_createteam);
		
		cd_yuancheng = new JButton(new ImageIcon("image/dialogimage/yuancheng.png"));
		cd_yuancheng.setBounds(194, 62, 36, 30);
		panel.add(cd_yuancheng);
		
		cd_sharedisplay = new JButton(new ImageIcon("image/dialogimage/sharedisplay.png"));
		cd_sharedisplay.setBounds(240, 62, 36, 30);
		panel.add(cd_sharedisplay);
		
		JButton cd_yingyong = new JButton(new ImageIcon("image/dialogimage/yingyong.jpg"));
		cd_yingyong.setBounds(286, 62, 36, 30);
		panel.add(cd_yingyong);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 91, 446, 259);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		textPane = new JTextPane();
		textPane.setBounds(0, 0, 446, 259);
		
		
		scrollPane_1 = new JScrollPane(textPane);
		scrollPane_1.setBounds(0, 0, 446, 259);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		panel_1.add(scrollPane_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 385, 446, 148);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textPane_1 = new JTextPane();
		textPane_1.setBounds(0, 44, 446, 53);
		panel_2.add(textPane_1);
		
		docMsg = textPane_1.getStyledDocument();
		docChat = textPane.getStyledDocument();
		
		cd_searchjilu = new JButton(new ImageIcon("image/dialogimage/jilu.jpg"));
		Cardjilu = new CardLayout(2, 2);
		cd_searchjilu.addActionListener(this);
		cd_searchjilu.setBounds(353, 11, 93, 23);
		panel_2.add(cd_searchjilu);
		
		picWindow = new QunPicsJWindow(this);
		
		panel_4 = new JPanel();
		panel_4.setVisible(false);
		
		panel_4.setBounds(0, 350, 446, 36);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		cd_size = new JComboBox();
		cd_size.setModel(new DefaultComboBoxModel(new String[] {"16", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		cd_size.setBounds(280, 9, 40, 21);
		cd_size.addActionListener(this);
		panel_4.add(cd_size);
		
		cd_jiacu = new JButton(new ImageIcon("image/dialogimage/jiacu.jpg"));
		cd_jiacu.setBounds(330, 10, 22, 20);
		panel_4.add(cd_jiacu);
		
		cd_xieti = new JButton(new ImageIcon("image/dialogimage/xieti.jpg"));
		cd_xieti.setBounds(362, 10, 22, 20);
		panel_4.add(cd_xieti);
		
		cd_xiahuaxian = new JButton(new ImageIcon("image/dialogimage/xiahuaxian.jpg"));
		cd_xiahuaxian.setBounds(394, 10, 22, 20);
		panel_4.add(cd_xiahuaxian);
		
		cd_color = new JButton(new ImageIcon("image/dialogimage/color.jpg"));
		cd_color.setBounds(426, 10, 20, 20);
		cd_color.addActionListener(this );
		panel_4.add(cd_color);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String script[] = ge.getAvailableFontFamilyNames();
		cd_ziti = new JComboBox();
		cd_ziti.setModel(new DefaultComboBoxModel(script));
		cd_ziti.addActionListener(this);
		cd_ziti.setBounds(182, 9, 88, 21);
		panel_4.add(cd_ziti);
		 
		cd_word = new JButton(new ImageIcon("image/dialogimage/word.png"));
		cd_word.addActionListener(this);
				
		cd_word.setBounds(10, 11, 30, 30);
		panel_2.add(cd_word);
		
		cd_biaoqing = new JButton(new ImageIcon("image/dialogimage/biaoqing.png"));
		cd_biaoqing.setBounds(39, 11, 30, 30);
		cd_biaoqing.addMouseListener(this);
		panel_2.add(cd_biaoqing);
		
		cd_magic = new JButton(new ImageIcon("image/dialogimage/magic.jpg"));
		cd_magic.setBounds(68, 11, 30, 30);
		panel_2.add(cd_magic);
		
		cd_zhendong = new JButton(new ImageIcon("image/dialogimage/zhendong.jpg"));
		cd_zhendong.setBounds(97, 11, 30, 30);
		cd_zhendong.addActionListener(this);
		panel_2.add(cd_zhendong);
		
		cd_yymessage = new JButton(new ImageIcon("image/dialogimage/yymessage.jpg"));
		cd_yymessage.setBounds(126, 11, 30, 30);
		panel_2.add(cd_yymessage);
		
		cd_dgninput = new JButton(new ImageIcon("image/dialogimage/dgninput.jpg"));
		cd_dgninput.setBounds(155, 11,30, 30);
		panel_2.add(cd_dgninput);
		
		cd_sendpic = new JButton(new ImageIcon("image/dialogimage/sendimage.jpg"));
		cd_sendpic.setBounds(184, 11, 30, 30);
		panel_2.add(cd_sendpic);
		
		cd_diange = new JButton(new ImageIcon("image/dialogimage/diange.jpg"));
		cd_diange.setBounds(214, 11, 30, 30);
		panel_2.add(cd_diange);
		
		cd_jietu = new JButton(new ImageIcon("image/dialogimage/jietu.jpg"));
		cd_jietu.setBounds(244, 11, 30, 30);
		cd_jietu.addActionListener(this);
		panel_2.add(cd_jietu);
		
		cd_close = new JButton(new ImageIcon("image/dialogimage/close.jpg"));
		cd_close.setBounds(307, 114, 64, 24);
		panel_2.add(cd_close);
		
		cd_send = new JButton(new ImageIcon("image/dialogimage/send.jpg"));
		cd_send.addActionListener(this);
		cd_send.setBounds(381, 114, 64, 24);
		panel_2.add(cd_send);
		
		panel_3 = new JPanel();
		panel_3.setBounds(444, 91, 285, 422);
		getContentPane().add(panel_3);
		

		panel_3.setLayout(Cardjilu);
		
		textPane_2 = new JTextPane();
		
		scrollPane_2 = new JScrollPane(textPane_2);
		scrollPane_2.setBounds(2, 2, 411, 418);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel_3.add(scrollPane_2, "name_3333");
		
		//panel_3.add(label1,"1");
		panel_3.add(Qunpane,"1");
		panel_3.add(scrollPane_2,"2");
		textPane_2.setBounds(2, 2, 411, 418);
		oldMsg = textPane_2.getStyledDocument();
		
		this.addComponentListener(new ComponentAdapter(){

			@Override
			public void componentResized(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}
			
		});
		
		cd_searchjilu.addActionListener(this);
			

		
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
        
        drag();///////////////�ϷŹ���
	}
	
	
	
	public Color recolor(String s)
	{
		String friend[]=s.split(",");
		String friend1[]= new String[friend.length];
		int j;
		for(int i =0; i < friend.length; i++)
		{
			j = friend[i].lastIndexOf("=");
			friend1[i] = friend[i].substring(j+1);
		}
		friend1[2] = friend1[2].substring(0, friend1[2].length()-1);
		return new Color(Integer.parseInt(friend1[0]),Integer.parseInt(friend1[1]),Integer.parseInt(friend1[2]));
	}
	
	/*
	 * �����յ��ı�����Ϣ��
	 */
	public void receivedPicInfo(String picInfos){
		String[] infos = picInfos.split("[+]");
		for(int i = 0 ; i < infos.length ; i++){
			String[] tem = infos[i].split("[&]");
			if(tem.length==2){
				PicInfo pic = new PicInfo(Integer.parseInt(tem[0]),tem[1]);
				receivdPicInfo.add(pic);
			}
		}
	}
	
	public void receivedoldPicInfo(String picInfos){
		String[] infos = picInfos.split("[+]");
		for(int i = 0 ; i < infos.length ; i++){
			String[] tem = infos[i].split("[&]");
			if(tem.length==2){
				PicInfo pic = new PicInfo(Integer.parseInt(tem[0]),tem[1]);
				receivdoldPicInfo.add(pic);
			}
		}
	}
	
	//дһ��������������ʾ��Ϣ
	public void showMessage(Message m)
	{
		String info=m.getSender()+" "+m.getSendTime()+" :\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        StyleConstants.setForeground(attrset, Color.green);
        StyleConstants.setFontFamily(attrset, "����");
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        info=m.getCon();
        int index = info.lastIndexOf("*");
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        docs = textPane.getDocument();
        pos1 = docs.getLength();
        if(index>0 && index < info.length()-1){
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	     //       jta.setCaretPosition(docChat.getLength()); // ���ù��������±�
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
			receivedPicInfo(info.substring(index+1,info.length()));
			insertPics(true);
		}else{
			
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	      //      jta.setCaretPosition(docChat.getLength()); // ���ù��������±�
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
		}

	}
	
	//дһ��������������ʾ��Ϣ
		public void showoldMessage(Message m)
		{
			String info=m.getSender()+" "+m.getSendTime()+" :\n";
			SimpleAttributeSet attrset = new SimpleAttributeSet();
	        StyleConstants.setFontSize(attrset,14);
	        StyleConstants.setForeground(attrset, Color.green);
	        StyleConstants.setFontFamily(attrset, "����");
	        Document docs = textPane_2.getDocument();
	        try {
	            docs.insertString(docs.getLength(), info, attrset);
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	        
	        info=m.getCon();
	        int index = info.lastIndexOf("*");
	        StyleConstants.setFontSize(attrset,m.getSize());
	        StyleConstants.setForeground(attrset, recolor(m.getCol()));
	        StyleConstants.setFontFamily(attrset, m.getFont());
	        docs = textPane_2.getDocument();
	        pos3 = docs.getLength();
	        if(index>0 && index < info.length()-1){
				try {
				//	JOptionPane.showMessageDialog(this,m.getCon());
		            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
		     //       jta.setCaretPosition(docChat.getLength()); // ���ù��������±�
		        } catch (BadLocationException e) {
		            e.printStackTrace();
		        }
				receivedoldPicInfo(info.substring(index+1,info.length()));
				insertoldPics();
			}else{
				
				try {
		            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
		      //      jta.setCaretPosition(docChat.getLength()); // ���ù��������±�
		        } catch (BadLocationException e) {
		            e.printStackTrace();
		        }
			}

		}
	
	public void showownMessage(Message m)
	{
		String info=m.getSender()+" "+m.getSendTime()+" :\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        StyleConstants.setForeground(attrset, Color.green);
        StyleConstants.setFontFamily(attrset, "����");
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        info=m.getCon()+"\r\n";
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        docs = textPane.getDocument();
        pos2 = docs.getLength();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        insertPics(false);
	}
	
	public void shake(Message m){
		showownMessage(m);
		setExtendedState(Frame.NORMAL);
		setVisible(true);
		new Thread(){
			long begin = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			Point p = QunChat.this.getLocationOnScreen();
			public void run(){
				int i = 1;
				while((end-begin)/1000<2){
					QunChat.this.setLocation(new Point((int)p.getX()-5*i,(int)p.getY()+5*i));
					end = System.currentTimeMillis();
					try {
						Thread.sleep(5);
						i=-i;
						QunChat.this.setLocation(p);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/**
	 * ����ͼƬ
	 * 
	 * @param isFriend �Ƿ�Ϊ���ѷ���������Ϣ
	 */
	int pos1;
	int pos2;
	private JButton sendfile;
	private JButton shake;
	private void insertPics(boolean isFriend) {
		if(isFriend){
			if(this.receivdPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < receivdPicInfo.size() ; i++){
					PicInfo pic = receivdPicInfo.get(i);
					String fileName;
					textPane.setCaretPosition(pos1+pic.getPos()); /*���ò���λ��*/
		            fileName= "face/"+pic.getVal()+".gif";/*�޸�ͼƬ·��*/ 
		            textPane.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*����ͼƬ*/
/*					jpChat.updateUI();*/
				}
				receivdPicInfo.clear();
			}
		}else{
			
			if(myPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < myPicInfo.size() ; i++){
					PicInfo pic = myPicInfo.get(i);
					
					textPane.setCaretPosition(pos2+pic.getPos()); /*���ò���λ��*/
					String fileName;
		            fileName= "face/"+pic.getVal()+".gif";/*�޸�ͼƬ·��*/ 
		            textPane.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*����ͼƬ*/
					/*jpChat.updateUI();*/
				}
				myPicInfo.clear();
			}
		}
		textPane.setCaretPosition(docChat.getLength()); /*���ù��������±�*/
		//insert(new FontAttrib()); /*���������Ի���*/
	}
	
	int pos3;
	private void insertoldPics() {
		
			if(this.receivdoldPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < receivdoldPicInfo.size() ; i++){
					PicInfo pic = receivdoldPicInfo.get(i);
					String fileName;
					textPane_2.setCaretPosition(pos3+pic.getPos()); /*���ò���λ��*/
		            fileName= "face/"+pic.getVal()+".gif";/*�޸�ͼƬ·��*/ 
		            textPane_2.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*����ͼƬ*/
/*					jpChat.updateUI();*/
				}
				receivdoldPicInfo.clear();
			}
			textPane_2.setCaretPosition(oldMsg.getLength()); /*���ù��������±�*/
	}
	
	/**
	 * ���鷢�͵ı�����Ϣ
	 * @return ��������Ϣ��  ��ʽΪ   λ��|����+λ��|����+����
	 */
	private String buildPicInfo(){
		StringBuilder sb = new StringBuilder("");
		//����jtextpane�ҳ����е�ͼƬ��Ϣ��װ��ָ����ʽ
		  for(int i = 0; i < this.textPane_1.getText().length(); i++){ 
              if(docMsg.getCharacterElement(i).getName().equals("icon")){
            	  
            	  //ChatPic = (ChatPic)
            	  Icon icon = StyleConstants.getIcon(textPane_1.getStyledDocument().getCharacterElement(i).getAttributes());
            	  ChatPic cupic = (ChatPic)icon;
            	  PicInfo picInfo= new PicInfo(i,cupic.getIm()+"");
            	  myPicInfo.add(picInfo);
            	  sb.append(i+"&"+cupic.getIm()+"+");
             } 
          }
		  System.out.println(sb.toString());
		  return sb.toString();
		  //return null;
	}
	
	
	
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		picWindow.setVisible(false);
	}
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if (getY() <= 0) {
			setLocation(getX(), 0);
		}
		if (e.getButton() != 1)
			return;/*�������*/

		JComponent source = (JComponent) e.getSource();
		/*����ͷ�ʱ���¼�Դ��,����Ӧ�����¼�*/
		if (e.getX() >= 0 && e.getX() <= source.getWidth() && e.getY() >= 0
				&& e.getY() <= source.getHeight()) {
			if (source == this.cd_biaoqing){
				picWindow.setVisible(true);
			}
		}
	}
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void windowOpened(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void windowClosing(WindowEvent e) {
		// TODO �Զ����ɵķ������
		ManageQqChat.RemoveQunChat(this.ownerId+" "+this.QunId);
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
	boolean c = true;
	int count=1;
	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
		
		
		if(arg0.getSource()==cd_shipin)
		{
			Message m=new Message();
			m.setMesType(MessageType.message_ret_ip);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
			m.setCon("");
			m.setMyport(999);
			m.setFriendport(888);
			m.setSendTime(new java.util.Date().toString());
			
			//���͸�������.
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}	
		}
		
		if(arg0.getSource()==cd_word)
		{
			if(c){
				panel_4.setVisible(true);
				c = false;
				}else{
					panel_4.setVisible(false);
					c = true;					
				}
		}
		
		if(arg0.getSource()==cd_searchjilu)
		{
			//this.setLayout(null);
			//this.setSize(728,533);
			//k.setBounds(446, 91, 283, 422);
			//this.add(k);
			
			if(count==1)
			{
				panel_3.setBounds(450, 90, 280, 400);
				Cardjilu.show(panel_3, "2");
			//	textPane_2
				count+=1;
			//	JOptionPane.showMessageDialog(this,"�û����������");
				
				Message m=new Message();
				m.setMesType(MessageType.message_Qun_oldmes);
				m.setSender(this.ownerId);
				m.setGetter(this.QunId);
				//m.setCon("old");///old�ǲ�ѯ��־	
				m.setSendTime(new java.util.Date().toString());
				
				//���͸�������.
				try {
					ObjectOutputStream oos=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
					oos.writeObject(m);
					
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}			
			}
			else if(count == 2)
			{
				count+=1;
			}
			else if(count == 3)
			{
				count+=1;
			}
			else if(count==4)
			{
				panel_3.setBounds(450, 90, 280, higth);
				Cardjilu.show(panel_3, "1");
				count=1;
				textPane_2.setText("");
			}
		}
		
		if(arg0.getSource()==cd_transfile)
		{
			JFileChooser fileDialog=new JFileChooser();
			fileDialog.showOpenDialog(this);
			File file=fileDialog.getSelectedFile();
			String type=file.getName().substring(file.getName().indexOf(".")+1);
			
			//JOptionPane.showMessageDialog(this,type);
			
			Message m=new Message();
			m.setMesType(MessageType.message_Qunfile);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
			m.setFile(type);
			
			try {
				fis=new DataInputStream(new FileInputStream(file));
				int size=fis.available();
				byte[] by=new byte[size];
				fis.read(by);
				fis.close();
				m.setFilebyte(by, size);
				String ss="";
				String s=null;
				
			    m.setSendTime(new java.util.Date().toString());
			    ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);				 				      
			    } catch (Exception e) {
				    e.printStackTrace();
				   // TODO: handle exception			    
			    }
		}
		
		if(arg0.getSource()==cd_zhendong)
		{
			Message m=new Message();
			m.setMesType(MessageType.message_shake);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
			
			m.setCol(Color.red.toString());
			m.setFont("����");
			m.setSize(14);
			
			DateFormat ddtf = DateFormat.getDateTimeInstance();  
			m.setSendTime(ddtf.format(new Date()));
			m.setCon(this.ownerId+" ������һ�����ڶ�����");
			this.showownMessage(m);
			//���͸�������.
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}		
		}
		
		
		if(arg0.getSource()==cd_send)
		{
			//����û�����ˣ����Ͱ�ť
			String pic = buildPicInfo();
			Message m=new Message();
			m.setMesType(MessageType.message_Qun_mes);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
		//	JOptionPane.showMessageDialog(this,jtf.getText()+"*"+buildPicInfo());
			m.setCon(textPane_1.getText());
			m.setCol(this.Text_color.toString());
			m.setFont(cd_ziti.getSelectedItem().toString());
			m.setSize(Integer.parseInt(cd_size.getSelectedItem().toString()));
			DateFormat ddtf = DateFormat.getDateTimeInstance();  
			m.setSendTime(ddtf.format(new Date()));
			this.showownMessage(m);
			
			m.setCon(textPane_1.getText()+"*"+pic);
		//	this.showMessage(m);
			this.textPane_1.setText("");
			//���͸�������.
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
		}
		if(arg0.getSource()==cd_ziti ||arg0.getSource()==cd_size)
		{
			textPane_1.setFont(new Font(cd_ziti.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(cd_size.getSelectedItem().toString())));
		//	
		}
		if(arg0.getSource()==cd_color)
		{
			Text_color=JColorChooser.showDialog(null,"��ѡ����ϲ������ɫ" ,Text_color);  
			textPane_1.setForeground(Text_color);
//			if (Text_color==null )
//				Text_color=Color.lightGray; 
		//	jtf.setFont(new Font(ziti.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(size.getSelectedItem().toString())));
	  
		}   
		if(arg0.getSource()==cd_exit)
		{
			ManageQqChat.RemoveQunChat(this.ownerId+" "+this.QunId);
			this.dispose();	
		}
		if(arg0.getSource()==cd_jietu)
		{
			 //ScreenCapture  k = new ScreenCapture();
			ScreenCapture.screenShot();
			 //k.dialog.setVisible(false);
			 //k.screenShot();
			//this.add(textPane_1);
		}
		
	}
	///////////////////////////�ϷŹ���
	public void drag()//�������ק����
    {
        //panel��ʾҪ������ק�Ŀؼ�
        new DropTarget(textPane_1, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter()
        {

            public void drop(DropTargetDropEvent dtde)//��д��������drop����
            {
                try
                {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//���������ļ���ʽ��֧��
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//������ק��������
                        List<File> list =  (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String filepath="";
                        for(File file:list)
                        filepath+=file.getAbsolutePath();                                         
                        dtde.dropComplete(true);//ָʾ��ק���������
                        
            			String type=filepath.substring(filepath.indexOf(".")+1);
            			
            			
            			//JOptionPane.showMessageDialog(this,type);
            			
            			Message m=new Message();
            			m.setMesType(MessageType.message_Qunfile);
            			m.setSender(ownerId);
            			m.setGetter(QunId);
            			m.setFile(type);
            			File  file=new File(filepath);
            			try {
            				fis=new DataInputStream(new FileInputStream(file));
            				int size=fis.available();
            				byte[] by=new byte[size];
            				fis.read(by);
            				fis.close();
            				m.setFilebyte(by, size);
            				String ss="";
            				String s=null;
            				
            			    m.setSendTime(new java.util.Date().toString());
            			    ObjectOutputStream oos=new ObjectOutputStream
            				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
            				oos.writeObject(m);				 				      
            			    } catch (Exception e) {
            				    e.printStackTrace();
            				   // TODO: handle exception			    
            			    }
                    }
                    else
                    {
                        dtde.rejectDrop();//����ܾ���ק��������
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
	public void setjiluText(String str)
	{
		k.setText(str);
	}
	public void addJLabel(String text,String text1)
	{
		
		higth+=30;
		record++;
		//QunId=text;
		boolean flag=false;
		//this.removeAll();
		//panel_3.setLayout(new GridLayout(record,1));
		for(int i=0;i<record-1;i++)
		{
			flag=jla[i].getText().equals(text1);
		}
		if(!flag)
		{
			jla[record-1]=new JLabel(text1,new ImageIcon("mm.jpg"),JLabel.LEFT);
			jla[record-1].addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(arg0.getClickCount()==2)
					{
						String friendId=((JLabel)arg0.getSource()).getText();
						QqChat qqchat=ManageQqChat.getQqChat(ownerId+" "+friendId);
						if(qqchat==null)
						{
							QqChat qqChat=new QqChat(ownerId,friendId);
							//�����������뵽������
							ManageQqChat.addQqChat(ownerId+" "+friendId, qqChat);
							qqChat.setVisible(true);
						}
						else
						{
							qqchat.setVisible(true);
						}
					}
				}
				public void mousePressed(MouseEvent e) {					
				}
				public void mouseReleased(MouseEvent e) {		
				}
				public void mouseEntered(MouseEvent e) {				
				}
				public void mouseExited(MouseEvent e) {				
				}});
			Qunpane.add(jla[record-1],"1");
		}
		else
		{
			higth-=30;
			record--;	
		}
		
		y=700-higth;
		Qunpane.setLayout(new GridLayout(record,1));
		//Qunpane.setLayout(null);
		panel_3.add(Qunpane,"1");
		panel_3.setLayout(Cardjilu);
		panel_3.setBounds(450, 90, 280, higth);
		Cardjilu.show(panel_3, "1");
		//this.setLayout(null);
		//panel_3.(panel_3, "1");
		Qunpane.setBounds(0,0, 280, higth);
	}
}

