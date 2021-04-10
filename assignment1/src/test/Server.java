package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

	public interface ClientHandler{
		public void handleClient(BufferedReader in, OutputStream out);
	}

	
	private int port;
	private ClientHandler ch;
	volatile boolean stop;
	public Server() {
		stop=false;
	}
	
	
	private void startServer(int port, ClientHandler ch) {
		this.port=port;
		this.ch=ch;
		try {
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);
		stop = false;
		while(!stop){
			try{
				Socket aClient=server.accept(); // blocking call
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
					ch.handleClient(in, aClient.getOutputStream());
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					//aClient.close();
				} catch (IOException e) {this.stop();}
			}catch(SocketTimeoutException e) {this.stop();}
		}
		server.close(); 
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// runs the server in its own thread
	public void start(int port, ClientHandler ch) {
		new Thread(()->startServer(port,ch)).start(); 
	}
	
	public void stop() {
		stop=true;
	}
}
