package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import test.AnomalyDetectionHandler.SocketIO;
import java.io.*;

public class Server {

	private int port;
	private ClientHandler ch;
	
	public interface ClientHandler{
		public void handleClient(BufferedReader inFromClient, PrintWriter outToClient);
	}

	volatile boolean stop;
	public Server() {
		stop=false;
	}
	
	
	private void startServer(int port, ClientHandler ch) 
	{
		try {
			ServerSocket server = new ServerSocket(port);
			server.setSoTimeout(1000);
			while(!stop)
			{
				Socket aClient = server.accept();
				BufferedReader inFromClient =new BufferedReader(new InputStreamReader(aClient.getInputStream()));
				PrintWriter outToClient = new PrintWriter(aClient.getOutputStream(),true);
				ch.handleClient(inFromClient, outToClient);
				outToClient.println("bye");
				//outToClient.write("\n");
				inFromClient.close();
				outToClient.close();
				aClient.close();
			}
			server.close();
		}
		catch (IOException e) {
			this.stop();
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
