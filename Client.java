package chatApplication;

import java.net.*;
import java.io.*;

public class Client {
	
	Socket socket;
	
	BufferedReader br;
	PrintWriter out;
	
	public Client() {
		try {
			
			//Socket(ip address, port number)
			System.out.println("Sending request to server...");
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("Connection done");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startReading() {
		Runnable r1 = () ->{
			System.out.println("Client reading started");
			
			while(true) {
				try {
					String msg = br.readLine();
					if(msg.equals("exit")) {
						System.out.println("Server Terminated...");
						break;
					}
					System.out.println("Server : "+ msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(r1).start();
	}
	
	public void startWriting() {
		Runnable r2 = ()->{
			System.out.println("Client writing started.... ");
			while(true) {
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				String content;
				try {
					content = br1.readLine();
					out.println(content);
					out.flush();
				} catch (Exception e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(r2).start();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("this is client");
		new Client();
	}

}
