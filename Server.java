package chatApplication;

import java.io.*;
import java.net.*;

public class Server {
	
	ServerSocket server;
	Socket socket;
	
	//these two variables are used to read and write of stream
	BufferedReader br;
	PrintWriter out;
	
	//Constructor
	public Server(){
		try {
			server = new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("waiting");
			
			//this server.accept is request of client 
			socket = server.accept();
			
			//this br contain the object of bufferedReader class. 
			//InputStreamReader converts the data from byte to char.
			// getInputStream fetch the data of socket in byte forms.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startReading() {
		// thread - using for data reading which is coming from client
		Runnable r1 = ()->{
			System.out.println("Reader started....");
			
			while (true) {
				try {
					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("Client Terminated");
						
						// used for closing the connection
						socket.close();
						break;
					}
					
					System.out.println("Cleint : "+msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(r1).start();
	}
	
	public void startWriting() {
		// thread - used for data sending to client.
		Runnable r2 = ()->{
			System.out.println("Writer Started....");
			while(true) {
				try {
					
					//this buffer is used to read data from console.
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					
					//data which we read from console is stored in br1.
					String content = br1.readLine();
					
					//thru out we send that console data to client.
					out.println(content);
					out.flush();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r2).start();
	}
	
	
	public static void main(String args[]) {
		System.out.println("this is server");
		new Server();
	}
}
