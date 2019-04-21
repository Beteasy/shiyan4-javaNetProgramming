package shiyan4;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		ServerThread serverThread = null;
		Socket socket = null;
		
		while(true) {
			try {
				serverSocket = new ServerSocket(2333);	//当2333端口被占用时会发生异常
			} 
			catch (IOException e) {
				System.out.println("正在监听");
			}
			try {
				System.out.println("正在等待客户");
				socket = serverSocket.accept();		//堵塞
				System.out.println("客户的地址:"+socket.getInetAddress());
			} 
			catch (IOException e) {
				System.out.println("正在等待客户");
			}
			if(socket != null) {
				serverThread = new ServerThread(socket);	//为每个客户启动一个服务线程,就这样写和书上的有什么区别？
				serverThread.start();
			}
		}
	}

}


class ServerThread extends Thread{
	
	Socket socket = null;
	DataInputStream in  = null;
	DataOutputStream out = null;
	
	ServerThread(Socket socket){
		this.socket = socket;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {}
	}
	
	public void run() {
		while(true) {
			try {
				String s = in.readUTF();
				String regex = ",";
				s = s.trim();
				String array[] = s.split(regex);
				double digit[] = new double[array.length];
				double sum = 0.0;
				for(int i=0; i<array.length; i++) {
					digit[i] = Double.parseDouble(array[i]);
					System.out.println(digit[i]);
					sum += digit[i];
				}
				System.out.println("本次总额:"+sum);
				//写出处理结果
				String s2 = "您的账单:\n"
						+ "账单房租:"+digit[0]+"元 水费:"+digit[1]+"元 电费:"+digit[2]+"元 物业费:"+digit[3]+"元\n"
						+"总额:"+sum+"元";
				out.writeUTF(s2);
			} catch (IOException e) {
				System.out.println("客户离开");
				return;
			}
		}
	}
}