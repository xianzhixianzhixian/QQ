package com.qq.client.view;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import javax.media.util.*;
import javax.media.format.*;

import com.qq.common.Message;
import com.sun.image.codec.jpeg.*;


public class VAplay extends JFrame implements Runnable,WindowListener,ActionListener{
	
	// ������Ƶͼ�񲥷���
	private static Player player = null;

	// ������Ƶ������
	private static Player player2 = null;

	// ��ȡ��Ƶ�豸
	private CaptureDeviceInfo device = null;

	// ��ȡ��Ƶ�豸
	private CaptureDeviceInfo device2 = null;

	// ý�嶨λ��
	private MediaLocator locator = null;

	private Image image;

	private Buffer buffer = null;

	private BufferToImage b2i = null;

	// ��������ͷ��������
	String str = "vfw:Microsoft WDM Image Capture (Win32):0";

	// ���岥���������
	Component comV, comVC, comA;

	//����
	JButton v_min,v_max,v_close;
	JLabel v_biaoti;
	//�²�
	JButton v_chat,v_close1,v_closesxt,v_bingpaiImage,v_photo,v_makeafun;
	private boolean isDragged = false;
	private Point loc = null;  
	private Point tmp = null;
	
	JPanel panel_1,panel,panel_2,panel_3;
	Thread thread1, thread2;
	
	int myport; int friendport;InetAddress address;
	private JLabel lab= new JLabel(new ImageIcon("image//load.gif"));
	
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		//Vedio vedio = new Vedio();
	}
	public VAplay(int myport, int friendport,InetAddress address){
		
		this.setSize(740, 424);
		
		this.setUndecorated(true);
		getContentPane().setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 740, 30);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		//������ɫ
		this.addWindowListener(this);
		this.myport = myport;
		this.friendport = friendport;
		this.address = address;
		String ip=address.getHostAddress().toString();
		
	
		v_biaoti = new JLabel("���ں�XXX��Ƶͨ��");
		v_biaoti.setForeground(Color.white);
		
		v_biaoti.setBounds(0, 0, 173, 30);
		panel_1.add(v_biaoti);
		
		v_min = new JButton(new ImageIcon("image/vedio/min.jpg"));
		v_min.setBounds(656, 0, 28, 28);
		panel_1.add(v_min);
		
		v_max = new JButton(new ImageIcon("image/vedio/max.jpg"));
		v_max.setBounds(684, 0, 28, 28);
		panel_1.add(v_max);
		
		v_close = new JButton(new ImageIcon("image/vedio/close.jpg"));
		v_close.setBounds(712, 0, 28, 28);
		v_close.addActionListener(this);
		panel_1.add(v_close);
		JLabel back1 = new JLabel(new ImageIcon("image/vedio/back.png"));
		back1.setBounds(0, 0, 740, 30);
		panel_1.add(back1);
		
		panel = new JPanel();
		panel.setBounds(0, 388, 740, 36);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		v_chat = new JButton(new ImageIcon("image/vedio/openchat.jpg"));
		v_chat.setBounds(10, 4, 38, 32);
		panel.add(v_chat);
		
		v_close1 = new JButton(new ImageIcon("image/vedio/close1.jpg"));
		v_close1.setBounds(654, 4, 58, 22);
		v_close1.addActionListener(this);
		panel.add(v_close1);
		
		v_closesxt = new JButton(new ImageIcon("image/vedio/closesxt.jpg"));
		v_closesxt.setBounds(110, 4, 30, 30);
		panel.add(v_closesxt);
		
		v_photo = new JButton(new ImageIcon("image/vedio/photo.jpg"));
		v_photo.setBounds(200, 4, 30, 30);
		panel.add(v_photo);
		
		v_makeafun = new JButton(new ImageIcon("image/vedio/makeafun.jpg"));
		v_makeafun.setBounds(160, 4, 30, 30);
		panel.add(v_makeafun);
		
		v_bingpaiImage = new JButton(new ImageIcon("image/vedio/bingpai.jpg"));
		v_bingpaiImage.setBounds(240, 4, 30, 30);
		panel.add(v_bingpaiImage);
		
		JLabel back2 = new JLabel(new ImageIcon("image/vedio/back.png"));
		back2.setBounds(0, 0, 740, 36);
		panel.add(back2);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 29, 740, 359);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		panel_3 = new JPanel();
		panel_3.setBounds(361, 57, 340, 240);
		panel_2.add(panel_3);
		
		lab.setBounds(0, 57, 340, 240);
		panel_2.add(lab);
		JLabel back3 = new JLabel(new ImageIcon("image/vedio/back.png"));
		back3.setBounds(0, 0, 740, 359);
		panel_2.add(back3);
		
		this.setVisible(true);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 510) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 290) / 2;
        
        try{
			// �ڱ��ز�����Ƶ
			jbInit();

			// �ڱ��ز�����Ƶ
			speaker();
		}catch (Exception e){

			e.printStackTrace();
		}

		thread1 = new Thread(this);
		thread2 = new Thread(this);

		// ������նԷ�����
		thread1.start();

		// ������Է���������
		thread2.start();

        
        
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
	
	// �ڱ��ز�����Ƶ

	private void jbInit() throws Exception

	{

		// ��ʼ���豸��strΪ�豸����

		device = CaptureDeviceManager.getDevice(str);

		// ȷ�������Э���ý����Դ��λ��

		locator = device.getLocator();

		try

		{

			// ����sethint��Manager�ᾡ����һ���ܺ�������������ʹ�õ�Renderer������������

			Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));

			// ͨ�����������������߳�ʹplayer�ﵽRealized״̬

			player = Manager.createRealizedPlayer(locator);

			player.start();

			if ((comV = player.getVisualComponent()) != null)

			// player.getVisualComponent()��һ��������Ƶý�������

			{

				panel_3.add(comV, "Center");

			}

			if ((comVC = player.getControlPanelComponent()) != null)

			// player.getControlPanelComponent()����ʾʱ������

			{

				panel_3.add(comVC, "South");

			}

		}

		catch (Exception e)

		{

			e.printStackTrace();

		}

		setBounds(0, 0, 740, 428);

		setVisible(true);

		int new_w = panel_3.getWidth(); // �����ͼ����

		int new_h = panel_3.getHeight(); // �����ͼ��߶�

		// MediaTracker�����һ��Image�����װ�أ����ͼ�����

		MediaTracker mt = new MediaTracker(this.panel_3);

		try

		{

			mt.addImage(image, 0);// װ��ͼ��

			mt.waitForID(0);// �ȴ�ͼ��ȫ��װ��

		}

		catch (Exception e)

		{

			e.printStackTrace();

		}

		// ��ͼ����Ϣд�뻺����

		BufferedImage buffImg = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = buffImg.createGraphics();

		g.drawImage(image, 0, 0, new_w, new_h, this.panel_3);

		g.dispose();

	}

	// �ڱ��ز�����Ƶ

	private void speaker() throws Exception

	{

		Vector deviceList = CaptureDeviceManager.getDeviceList(new

		AudioFormat(AudioFormat.LINEAR, 44100, 16, 2));

		if (deviceList.size() > 0)

		{

			device2 = (CaptureDeviceInfo) deviceList.firstElement();

		}

		else

		{

			System.out.println("�Ҳ�����Ƶ�豸��");

		}

		try

		{

			player2 = Manager.createRealizedPlayer(device2.getLocator());

			player2.start();

			if ((comA = player2.getControlPanelComponent()) != null)

			{

			//	p3.add(comA, "South");

			}

		}

		catch (Exception e)

		{

			e.printStackTrace();

		}

	}

	// �̣߳����ջ�����Ƶͼ������

	public void run()

	{

		DatagramPacket pack = null;

		DatagramSocket maildata = null;

		byte data[] = new byte[320 * 240];

		try

		{

			// �������ݰ�

			pack = new DatagramPacket(data, data.length);

			// �������ݱ����հ�

			maildata = new DatagramSocket(this.myport);

		}

		catch (Exception e) {
		}

		while (true)

		{

			if (Thread.currentThread() == thread1)

			{

				if (maildata == null)

				{

					break;

				}

				else

				{

					try

					{

						// ����

						maildata.receive(pack);

						ByteArrayInputStream input = new ByteArrayInputStream(
								data);

						Image message = ImageIO.read(input);

						// �ڽ���ͼ�񴰿���ʾ��Ƶͼ��

						lab.setIcon(new ImageIcon(message));

					}

					catch (Exception e)

					{

						System.out.println("����ͼ������ʧ�ܣ�");

					}

				}

			}

			else if (Thread.currentThread() == thread2)

			{

				try

				{

					// ����Ҫ�ڲ��Ŵ�����ʾ��ͼ��֡

					FrameGrabbingControl fgc = (FrameGrabbingControl) player
							.getControl(

							"javax.media.control.FrameGrabbingControl");

					// ��ȡ��ǰ��������Buffer��

					buffer = fgc.grabFrame();

					b2i = new BufferToImage((VideoFormat) buffer.getFormat());

					image = b2i.createImage(buffer); // ת��Ϊͼ��

					// ����imageͼ������С��ͼ�񻺳���

					BufferedImage bi = (BufferedImage) createImage(
							image.getWidth(null),

							image.getHeight(null));

					// ����BufferedImage���󴴽�Graphics2D����

					Graphics2D g2 = bi.createGraphics();

					g2.drawImage(image, null, null);

					ByteArrayOutputStream output = new ByteArrayOutputStream();

					// ת����JPEGͼ���ʽ

					JPEGImageEncoder encoder = JPEGCodec
							.createJPEGEncoder(output);

					JPEGEncodeParam jpeg = encoder
							.getDefaultJPEGEncodeParam(bi);

					jpeg.setQuality(0.5f, false);

					encoder.setJPEGEncodeParam(jpeg);

					encoder.encode(bi);

					output.close();

					

					DatagramPacket datapack1 = new DatagramPacket(
							output.toByteArray(), output.size(),

							this.address, this.friendport);

					DatagramSocket maildata1 = new DatagramSocket();

					maildata1.send(datapack1);

					Thread.sleep(400);

				}

				catch (Exception e)

				{

					System.out.println("��Ƶ����ʧ�ܣ�");

				}

			}

		}
	}
	
	
	public void windowOpened(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void windowClosing(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
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
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==v_close||e.getSource()==v_close1)
		{
			player.close();
			player2.close();
			this.dispose();
		}
	}
	
}
