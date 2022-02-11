package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class GUIchat extends JFrame {

	Socket socket;
	private JPanel contentPane;
	private JTextField textField;
	private String userinput;
	private static BufferedReader in = null;
	private static PrintWriter out = null;
	private JTextPane textPane;
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					GUIchat frame = new GUIchat();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public GUIchat(String name, Socket socket, String nameGuest) {
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

		} catch(Exception e) {
			System.out.println();
		}
		
		setTitle(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 175, 175));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				Thread wait = new Thread() {
					@Override
					public void run() {
						while(true) {
							try {
								String res = in.readLine();
								if(res.indexOf("/message") != -1) {
									String content = res.replace("/message", "");
									textPane.setText(textPane.getText()+ "\n" + nameGuest+": " + content);
								}
								
								if(res.equals("/exit")) {
									out.println("/exited");
									GUIqueue queue = new GUIqueue(socket, name);
									dispose();
									queue.setVisible(true);
									return;
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				
				wait.start();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		textPane = new JTextPane();
		textPane.setEditable(false);

		textPane.setBounds(10, 11, 414, 205);
		contentPane.add(textPane);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userinput = textField.getText();
	        	System.out.println(name + ": "+userinput);
	        	textPane.setText(textPane.getText()+ "\n" + name+ ": " + userinput);
	        	textField.setText("");
	        	out.println("/message"+userinput);
			}
		});
		textField.setBounds(10, 228, 215, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("G\u1EEDi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
//				
//
////				returning the output to the server : true statement is to flush the buffer otherwise we have to do it manually
//				out = new PrintWriter(socket.getOutputStream(), true);
				userinput = textField.getText();
	        	System.out.println(name + ": "+userinput);
	        	textPane.setText(textPane.getText()+ "\n" + name+ ": " + userinput);
	        	textField.setText("");
	        	out.println("/message"+userinput);
//	        	out.println(userinput);
			}
		});
		btnNewButton.setBounds(235, 228, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("R\u1EDDi kh\u1ECFi");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				out.println("/exit");
				GUIqueue queue = new GUIqueue(socket, name);
				dispose();
				queue.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(334, 228, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
