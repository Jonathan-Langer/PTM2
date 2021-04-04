package test;


import java.io.*;
import java.net.Socket;
//import java.io.IOException;

import test.Commands.DefaultIO;
import test.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler
{

	public class SocketIO implements DefaultIO
	{
		BufferedReader inToSocket;
		PrintWriter outFromSocket;
		
		public SocketIO(Socket socket)
		{
			try {
				this.inToSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.outFromSocket = new PrintWriter(socket.getOutputStream(),true);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public SocketIO(BufferedReader in, PrintWriter out)
		{
			this.inToSocket = in;
			this.outFromSocket = out;
		}
		
		@Override
		public String readText() {
			try {
				return inToSocket.readLine();
			} 
			catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void write(String text) {
			outFromSocket.write(text);
		}

		@Override
		public float readVal() {
			Float f = null;
				try {
					Float value = Float.parseFloat(inToSocket.readLine());
					return value;
				} 
				catch (NumberFormatException e) {
					e.printStackTrace();
					return f;
				} 
				catch (IOException e) {
					e.printStackTrace();
					return f;
				}
		}

		@Override
		public void write(float val) {
			outFromSocket.print(val);
		}
	}

	@Override
	public void handleClient(BufferedReader inFromClient, PrintWriter outToClient) {
		DefaultIO ioForClient = new SocketIO(inFromClient,outToClient);
		CLI commandLineInterface = new CLI(ioForClient);
		commandLineInterface.start();
	}
}
