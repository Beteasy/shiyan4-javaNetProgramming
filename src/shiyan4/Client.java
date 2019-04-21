package shiyan4;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
	public static void main(String[] args) {
		ClientWindow clientWindow = new ClientWindow();
	}

}

class ClientWindow extends JFrame implements Runnable, ActionListener{
	private static final long serialVersionUID = 1L;
	JButton connectButton, sendButton;
	JLabel rentLabel, waterLabel, electricityLabel, wuyeLabel;
	JTextField rentText, waterText, electricityText, wuyeText;
	JTextArea area;
	JScrollPane scroll;
	Socket socket = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	Thread thread;	//客户端的线程用来不断读取服务器信息响应
	
	ClientWindow(){
		init();
		this.setBounds(600, 200, 600, 300);
		this.setTitle("客户端");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void init(){
		this.setLayout(new FlowLayout());
		connectButton = new JButton("连接服务器");
		rentText = new JTextField(5);
		waterText = new JTextField(5);
		electricityText = new JTextField(5);
		wuyeText = new JTextField(5);
		sendButton = new JButton("发送");
		sendButton.setEnabled(false);
		rentLabel = new JLabel("房租:");
		waterLabel = new JLabel("水费:");
		electricityLabel = new JLabel("电费:");
		wuyeLabel = new JLabel("物业费:");
		area = new JTextArea(8,8);
		scroll = new JScrollPane(area);
		
		Box boxH1, boxH2, boxH3, boxBase;
		boxH1 = Box.createHorizontalBox();
		boxH2 = Box.createHorizontalBox();
		boxH3 = Box.createHorizontalBox();
		boxBase = Box.createVerticalBox();
		
		boxH1.add(connectButton);
		boxH2.add(scroll);
		
		boxH3.add(rentLabel);
		boxH3.add(rentText);
		boxH3.add(new JLabel("元  "));
		boxH3.add(waterLabel);
		boxH3.add(waterText);
		boxH3.add(new JLabel("元  "));
		boxH3.add(electricityLabel);
		boxH3.add(electricityText);
		boxH3.add(new JLabel("元  "));
		boxH3.add(wuyeLabel);
		boxH3.add(wuyeText);
		boxH3.add(new JLabel("元  "));
		boxH3.add(sendButton);
		
		boxBase.add(boxH1);
		boxBase.add(boxH2);
		boxBase.add(boxH3);
		this.add(boxBase);
		
		connectButton.addActionListener(this);
		sendButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectButton) {
			socket = new Socket();
			thread = new Thread();
			InetAddress inetAddress;
			try {
				if(socket.isConnected()) {
					//如果已连接，则什么也不做
				}
				else {
					//建立连接
					inetAddress = InetAddress.getByName("127.0.0.1");
					InetSocketAddress endpoint = new InetSocketAddress(inetAddress,2333);
					socket.connect(endpoint);
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					sendButton.setEnabled(true);
					if(!(thread.isAlive())) {
						thread = new Thread(this);
					}
					thread.start();
				}
			}
			catch (Exception e1) {
				System.out.println(e1);
				socket = new Socket();
			}
		}
		if(e.getSource() == sendButton) {
			String s = rentText.getText()+","+waterText.getText()+","+electricityText.getText()+","+wuyeText.getText();
			String s2 = "输入账单:\n"
					+ "房租:"+rentText.getText()+"元 水费:"+waterText.getText()+"元 电费:"
					+electricityText.getText()+"元 物业费:"+wuyeText.getText()+"元\n";
			area.append(s2);
			try {
				out.writeUTF(s);
			} catch (IOException e1) {}
		}
	}

	@Override
	public void run() {
		String s = null;
		while(true) {
			try {
				s = in.readUTF();
				area.append(s);
			} catch (IOException e) {
				area.append("与服务器断开连接");
				socket = new Socket();
				break;
			}
		}
	}
	
}
