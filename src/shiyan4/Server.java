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
				serverSocket = new ServerSocket(2333);	//��2333�˿ڱ�ռ��ʱ�ᷢ���쳣
			} 
			catch (IOException e) {
				System.out.println("���ڼ���");
			}
			try {
				System.out.println("���ڵȴ��ͻ�");
				socket = serverSocket.accept();		//����
				System.out.println("�ͻ��ĵ�ַ:"+socket.getInetAddress());
			} 
			catch (IOException e) {
				System.out.println("���ڵȴ��ͻ�");
			}
			if(socket != null) {
				serverThread = new ServerThread(socket);	//Ϊÿ���ͻ�����һ�������߳�,������д�����ϵ���ʲô����
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
				System.out.println("�����ܶ�:"+sum);
				//д��������
				String s2 = "�����˵�:\n"
						+ "�˵�����:"+digit[0]+"Ԫ ˮ��:"+digit[1]+"Ԫ ���:"+digit[2]+"Ԫ ��ҵ��:"+digit[3]+"Ԫ\n"
						+"�ܶ�:"+sum+"Ԫ";
				out.writeUTF(s2);
			} catch (IOException e) {
				System.out.println("�ͻ��뿪");
				return;
			}
		}
	}
}