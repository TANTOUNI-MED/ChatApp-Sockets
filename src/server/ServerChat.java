package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChat extends Thread {
		private int nbClient;
		private Conversation cnv;
		private ArrayList<Conversation> Clients=new ArrayList<Conversation>();
		public static void main(String[] args) {
			new ServerChat().start();
		}
		@Override 
		public void run() {
			try {
				System.out.println("the server is runing");
				ServerSocket serverSocket=new ServerSocket(6666);
				while(true) {
					Socket sk=serverSocket.accept();
					++nbClient;
					cnv=new Conversation(sk,nbClient);
					Clients.add(cnv);
					cnv.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		class Conversation extends Thread{
			protected Socket socket;
			protected int nbClient;
			 public Conversation(Socket sk,int nb) {
				 this.socket=sk;
				 this.nbClient=nb;
			 }
			public void sendMsg(String s,Socket sk,int nb){
				try {
					for(Conversation client:Clients) {
					if (client.socket!=sk) {
						if(client.nbClient==nb || nb==-1) {
							OutputStream os=client.socket.getOutputStream();
						PrintWriter pw=new PrintWriter(os,true);
						String ss=nbClient+"=>"+s;
						pw.println(ss);
						}
						
					}
				}
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
			 }
			 @Override
			 public void run() {
				 
				try {
					InputStream is= socket.getInputStream();;
				    InputStreamReader isr=new InputStreamReader(is);
				    BufferedReader br = new BufferedReader(isr);
				    
				    OutputStream os=socket.getOutputStream();
				    PrintWriter pw = new PrintWriter(os,true);
				    
				    System.out.println("the client "+nbClient+" is connected ");
				    pw.println("hey your number is "+nbClient+" welecom... ");
				    
				    while(true) {
				    	String req=br.readLine();
				    	if(req.contains("=>")) {
				    		String[] msgParams=req.split("=>");
				    	String msg=msgParams[1];
				    	int nbCli=Integer.parseInt(msgParams[0]);
				    	sendMsg(msg,socket,nbCli);
				    	}else {
				    		sendMsg(req,socket,-1);
				    	}
				    	
				    	
				    	
				    }
				    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 
				 
			 }
		}

}
