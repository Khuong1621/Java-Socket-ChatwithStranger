

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
	public Socket socket,socketclient;
	public int status=0;
	public PrintWriter out,outcl;
	public ArrayList<ServerThread> threadList;
	public String name = "";
	
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
		this.socket = socket;
		this.threadList = threads;
	}
		
	@Override
	public void run() {
		try {
//			Reading input from client
			BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));

			
//			returning the output to the client : true statement is to flush the buffer otherwise we have to do it manually
			out = new PrintWriter(socket.getOutputStream(), true);
			//outcl = new PrintWriter(socketclient.getOutputStream(), true);
// 
			
			while(name.equals("")) {
				String rname = in.readLine();
// kiem tra ten trong thread
				if(checkname(rname)) {
					out.println("/ok");
					name = rname;
				}else {
					out.println("/meet");
					
				}
			}

			
//			inifite loop for server
			while(true) {
				String line = in.readLine();
				if (line.equals("/find")) {
//					System.out.println("find");
					for (ServerThread serverThread : threadList) {	
// filter busy client
						if(serverThread.name.equals(this.name) || serverThread.status == 1) {
							continue;
						}else {
							out.println(serverThread.name);
							String reply= in.readLine();
							if(reply.equals("/agreetoconnect")) {
								socketclient = serverThread.socket;
								status = 1; //pusy
								PrintWriter op = new PrintWriter(socketclient.getOutputStream(), true);
								op.println("/connect"+ this.name);
							}
						}
					}
					out.println("/outofGuest");
				}
				
				//Bi dong ket noi
				if(line.indexOf("/startChat") != -1) {
					String nameGuest = line.replace("/startChat", "");
					for (ServerThread serverThread : threadList) {
						if(serverThread.name.equals(nameGuest)) {
							this.status = 1;
							socketclient = serverThread.socket;
						}
					}
				}
				
				//Gui tin nhan
				if(line.indexOf("/message") != -1) {
					//System.out.println("gui tin nhan");
					PrintWriter op = new PrintWriter(socketclient.getOutputStream(), true);
					String content = line.replace("/message", "");
					op.println(line);
				}
				
				//thoat chat
				if(line.equals("/exit")) {
					PrintWriter op = new PrintWriter(socketclient.getOutputStream(), true);
					op.println("/exit");
					this.status = 0;
					this.socketclient = null;
				}
				
				//Bi thoat chat
				if(line.equals("/exited")) {
					this.status = 0;
					this.socketclient = null;
				}
//				System.out.println("Server get: " + line);
//
//				out.println(line);
				}
///				
			
		} catch (Exception e) {
			System.err.println("Client closed");
			try {
				socket.close();
			} catch (IOException e1) {
				System.err.println(e1);;
			
			}
		}
	}
	public boolean checkname(String name) {
		for (ServerThread serverThread : threadList) {
			if(name.equals(serverThread.name)) {
				return false;
			}
		}
		return true;
	}
//	private void printToAllClient(String outputString) {
//		for(ServerThread sT: threadList) {
//			sT.output.println(outputString);
//		}
//	}
	
	
}
