package ba.bitcamp.exercise.Benjo.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIChat implements Runnable {

	private JTextArea display;
	private JTextField messageIn;
	private Socket connection;
	private InputStream in;
	private OutputStream out;

	public GUIChat(Socket connection) throws IOException {
		this.connection = connection;
		this.in = connection.getInputStream();
		this.out = connection.getOutputStream();

		JFrame window = new JFrame("GUI chat");
		JPanel panel = new JPanel();
		window.setLocation(700, 300);
		window.setSize(400, 300);
		window.setPreferredSize(new Dimension(400, 300));
		

		display = new JTextArea();
		display.setEditable(false);
		// display.setPreferredSize(new Dimension( 390,220));
		display.setLineWrap(true);
		display.setWrapStyleWord(true);

		/*
		 * JScrollPane je "veci" od JTextArea, tako da se na njega lijepi
		 * JTextArea, a onda se on zalijepi na JPanel ili JFrame
		 */
		JScrollPane scrollPane = new JScrollPane(display);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(390, 220));
		
		JButton buttonSend = new JButton("Send");

		ButtonHandler listener = new ButtonHandler();
		buttonSend.addActionListener(listener);
		
		messageIn = new JTextField();
		messageIn.setPreferredSize(new Dimension(250, 30));
		messageIn.addKeyListener(listener);

		// messageIn.addKeyListener(new KeyAdapter() {
		//
		// @Override
		// public void keyTyped(KeyEvent e) {
		// if (!messageIn.getText().equals("")) {
		// if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		// String enteredMsg = messageIn.getText();
		// System.out.println("Me: " + enteredMsg);
		// messageIn.setText("");
		//
		// display.append("Me: " + enteredMsg + "\n");
		// }
		// }
		// }
		// });
		

		panel.add(scrollPane);

		panel.add(messageIn);
		panel.add(buttonSend);

		window.setContentPane(panel);

		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void ListenForNetwork() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(in));

		String line = null;
		while ((line = input.readLine()) != null) {
			if (!line.equals("")) {
				display.append("Client: " + line + "\n");
				line = null;
			}

		}

	}

	// moze se i ovdje dodati extends KeyAdapter umjesto da bude anonimna klasa
	public class ButtonHandler extends KeyAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// String enteredMsg = messageIn.getText();
			// System.out.println("Me: "+ enteredMsg);
			// messageIn.setText("");
			sendMessage();

		}

		private void sendMessage() {
			String str = messageIn.getText();
			// if (!messageIn.getText().equals("")) {
			if (!str.equals("")) {
				str += "\n";
				display.append("Me: " + messageIn.getText() + "\n");
				try {
					out.write(str.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				messageIn.setText(null);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
//			String str = messageIn.getText();
//			if (!messageIn.getText().equals("")) {
//				if (!str.equals("")) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					sendMessage();
//				}
			}
		}

	}

	@Override
	public void run() {
		try {
			ListenForNetwork();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
