package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import test.Commands.DefaultIO;
import test.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler{

	AnomalyDetectionHandler(){}
	
	public class SocketIO implements DefaultIO{
		
		Scanner in;
		PrintWriter out;//
		
		public SocketIO(BufferedReader bufferedReader, OutputStream output) {
				in=new Scanner(bufferedReader);
				out=new PrintWriter(output);
		}
	
		@Override
		public String readText() {
			 return in.nextLine();
		}

		@Override
		public void write(String text) {
			out.print(text);
		}

		@Override
		public float readVal() {
			return in.nextFloat();
		}

		@Override
		public void write(float val) {
			out.print(val);
		}

		public void close() {
			out.print("bye\n");
			out.flush();
			//in.close();
			//out.close();
		}
	}

	@Override
	public void handleClient(BufferedReader input, OutputStream output) {
		DefaultIO sio = new SocketIO(input,output);
		CLI c = new CLI(sio);
		c.start();
	}


}
