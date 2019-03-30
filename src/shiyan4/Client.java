package shiyan4;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		ClientWindow client2 = new ClientWindow();
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
	Thread thread;
	
	ClientWindow(){
		init();
		this.setBounds(600, 200, 600, 300);
		this.setTitle("客户端");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		socket = new Socket();
		thread = new Thread();
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
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		try {
			InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress,23333);
		
	}
	
}
