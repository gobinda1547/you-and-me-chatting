package org.gobinda.extra;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.gobinda.design.MainFrame;

public class User {

	public static int totalMessageSendOrReceive;
	private static User user;

	private Socket socket;

	private DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	public User(Socket sock) throws IOException {

		totalMessageSendOrReceive = 0;
		socket = sock;
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (socket.isConnected()) {
					try {
						String message = dataInputStream.readUTF();
						MainFrame.showReceivedMessage(message);
						SoundManager.playSound();
						totalMessageSendOrReceive++;
					} catch (Exception e) {
						User.stopServerManager();
						MainFrame.showPanelOnCardLayout("initialPanel");
						JOptionPane.showMessageDialog(null, "Connection lost!");
						break;
					}
				}
			}
		}).start();
	}

	public static void sendMessage(String message) {
		user.sendMessages(message);
	}

	private void sendMessages(String message) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					dataOutputStream.writeUTF(message);
					dataOutputStream.flush();
					totalMessageSendOrReceive++;
				} catch (IOException e) {
					User.stopServerManager();
					MainFrame.showPanelOnCardLayout("initialPanel");
					JOptionPane.showMessageDialog(null, "Connection lost!");
				}
			}
		});
	}

	public static boolean startUserService(Socket socket) {
		try {
			user = new User(socket);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static void stopServerManager() {
		if (user.socket != null) {
			try {
				user.socket.close();
			} catch (IOException e) {
			}
		}
		user = null;
	}

}
