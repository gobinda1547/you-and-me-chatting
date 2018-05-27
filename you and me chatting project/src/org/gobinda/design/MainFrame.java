package org.gobinda.design;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.CardLayout;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import org.gobinda.extra.SoundManager;
import org.gobinda.extra.User;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	private static int PORT_USED = 55555;
	private static MainFrame mainFrame;
	private static CardLayout card;

	private static final long serialVersionUID = 1L;

	private static JPanel contentPane;
	private static JTextArea chatArea;

	private JTextField serverIpTextField;
	private JTextField clientIpTextField;
	private JTextField textField;

	public static void initilize() {
		mainFrame = new MainFrame();
	}

	public static void showOrHideMainFrame(boolean tof) {
		mainFrame.setVisible(tof);
	}

	public static void showPanelOnCardLayout(String panelName) {
		card.show(contentPane, panelName);
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 414);
		setLocationRelativeTo(null);

		card = new CardLayout();

		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(card);

		JPanel initialPanel = new JPanel();
		contentPane.add(initialPanel, "initialPanel");
		initialPanel.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(null);
		panel_2.setBounds(10, 11, 308, 344);
		initialPanel.add(panel_2);

		JLabel label = new JLabel("SERVER");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label.setBounds(10, 11, 288, 34);
		panel_2.add(label);

		serverIpTextField = new JTextField();
		serverIpTextField.setText("127.0.0.1");
		serverIpTextField.setHorizontalAlignment(SwingConstants.CENTER);
		serverIpTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		serverIpTextField.setColumns(10);
		serverIpTextField.setBounds(75, 70, 161, 34);
		panel_2.add(serverIpTextField);

		JButton button = new JButton("MINE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					serverIpTextField.setText(InetAddress.getLocalHost().getHostAddress().toString());
				} catch (UnknownHostException e) {
					serverIpTextField.setText("Can not access your IP address.");
				}
			}
		});
		button.setBounds(112, 128, 89, 23);
		panel_2.add(button);

		JButton button_1 = new JButton("START");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							@SuppressWarnings("resource")
							ServerSocket serverSocket = new ServerSocket(PORT_USED);
							Socket socket = serverSocket.accept();
							if (User.startUserService(socket)) {
								MainFrame.showPanelOnCardLayout("chatPanel");
								serverSocket.close();
								return;
							}
							
							JOptionPane.showMessageDialog(null, "Can not Open Server!");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Can not Open Server!");
							User.stopServerManager();
							e.printStackTrace();
						}
					}
				});
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_1.setBounds(75, 182, 161, 34);
		panel_2.add(button_1);

		JButton button_2 = new JButton("CANCEL");
		button_2.setBounds(112, 248, 89, 23);
		panel_2.add(button_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setLayout(null);
		panel_3.setBounds(328, 11, 308, 344);
		initialPanel.add(panel_3);

		JLabel label_1 = new JLabel("CLIENT");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label_1.setBounds(10, 11, 288, 34);
		panel_3.add(label_1);

		clientIpTextField = new JTextField();
		clientIpTextField.setText("127.0.0.1");
		clientIpTextField.setHorizontalAlignment(SwingConstants.CENTER);
		clientIpTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clientIpTextField.setColumns(10);
		clientIpTextField.setBounds(75, 70, 161, 34);
		panel_3.add(clientIpTextField);

		JButton button_3 = new JButton("NEW");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientIpTextField.setText("");
			}
		});
		button_3.setBounds(112, 128, 89, 23);
		panel_3.add(button_3);

		JButton button_4 = new JButton("START");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					@SuppressWarnings("resource")
					@Override
					public void run() {
						try {
							String destIP = clientIpTextField.getText().trim();
							Socket socket = new Socket(destIP, PORT_USED);
							if (User.startUserService(socket)) {
								MainFrame.showPanelOnCardLayout("chatPanel");
								return;
							} else {
								JOptionPane.showMessageDialog(null, "Connection Error!");
							}

						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Connection Error!");
							User.stopServerManager();
						}
					}
				});
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_4.setBounds(75, 182, 161, 34);
		panel_3.add(button_4);

		JButton button_5 = new JButton("CANCEL");
		button_5.setBounds(112, 248, 89, 23);
		panel_3.add(button_5);

		JPanel chatPanel = new JPanel();
		contentPane.add(chatPanel, "chatPanel");
		chatPanel.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User.sendMessage(textField.getText());
				MainFrame.showSendedMessage(textField.getText());
				textField.setText("");
			}
		});
		chatPanel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);

		JPanel panel = new JPanel();
		chatPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User.stopServerManager();
				MainFrame.showPanelOnCardLayout("initialPanel");
			}
		});
		panel.add(btnLogout);

		JButton btnNewButton = new JButton("Chat Sound is Currently ON");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SoundManager.changeSoundSettings();
				String buttonText = btnNewButton.getText();
				if (btnNewButton.getText().endsWith("ON")) {
					btnNewButton.setText(buttonText.replace("ON", "OFF"));
				} else {
					btnNewButton.setText(buttonText.replace("OFF", "ON"));
				}
			}
		});
		panel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("You are now Connected With 127.0.0.1");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		chatPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		chatArea = new JTextArea();

		DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		scrollPane.setViewportView(chatArea);
	}

	public static void showReceivedMessage(String message) {
		chatArea.append("\t" + message + "\n");
		// chatArea.setCaretPosition(User.totalMessageSendOrReceive);
	}

	public static void showSendedMessage(String message) {
		chatArea.append(message + "\n");
		// chatArea.setCaretPosition(User.totalMessageSendOrReceive);
	}
}
