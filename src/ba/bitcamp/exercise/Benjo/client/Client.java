package ba.bitcamp.exercise.Benjo.client;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import ba.bitcamp.exercise.Benjo.GUI.GUIChat;



public class Client {

	public static final int PORT = 1717;
	public static final String ServerADDRESS = "localHost"; //"127.0.0.1"
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket(ServerADDRESS,PORT);
			GUIChat chatClient = new GUIChat(client);
//			OutputStream out = client.getOutputStream();
//			out.write("Hello World\n".getBytes());
//			out.flush();
//			while (true){
			new Thread(chatClient).start();	
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
