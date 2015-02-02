package ba.bitcamp.exercise.Benjo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ba.bitcamp.exercise.Benjo.GUI.GUIChat;

public class Server {

	public static final int PORT = 1717;

	public static void serverStart() throws IOException {

		ServerSocket server = new ServerSocket(PORT);
		while (true) {
			String str = "waiting..";
			System.out.println(str);
			Socket client =  server.accept();
			GUIChat chat = new GUIChat(client);
//			Thread t = new Thread(chat);
//			t.start();
			new Thread(chat).start();
			chat.ListenForNetwork();
		}
	}
	
	public static void main(String[] args) {
		try {
			serverStart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
