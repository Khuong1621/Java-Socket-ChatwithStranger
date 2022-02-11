package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Color;

public class GUIClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static Socket socket = null;
	private static BufferedReader in = null;
	private static BufferedWriter out = null;
	private static BufferedReader stdIn = null;
	private JLabel lbl;
	private String name,error;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUIClient frame = new GUIClient();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public GUIClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(25, 125, 236, 33);
		
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u0110\u1ED3ng \u00FD");
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				textField.setVisible(false);
//				lbl.setText("Đang chờ ghesp cawp ...");
//				btnNewButton.setVisible(false);
//				JOptionPane.showMessageDialog(contentPane, "Found");
				name = textField.getText();
				try {
					
					if(checkname()) {
						GUIqueue q = new GUIqueue(socket, name);
						dispose();
						q.setVisible(true);
						
						
						//yeu cau tim nguoi
//						
					}else {
						JOptionPane.showMessageDialog(contentPane, error);
						textField.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//				GUIchat gc = new GUIchat();
//				gc.setVisible(true);
//				dispose();
			}
		});
		btnNewButton.setBounds(307, 129, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Welcome!!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 33));
		lblNewLabel.setBounds(92, 16, 238, 33);
		contentPane.add(lblNewLabel);
		
		lbl = new JLabel("Nh\u1EADp nickname \u0111\u1EC3 tham gia chat");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lbl.setBounds(92, 60, 259, 59);
		contentPane.add(lbl);
        
	}
	private boolean checkname() throws IOException {
		if(name.equals("")) {
			error = "Tên không được bỏ trống";
			return false;
		}
		out.write(name);
		out.newLine();
		out.flush();
		String line = in.readLine();
		if(!line.equals("/ok")) {
			error= "Tên đã được chọn";
			return false;
		}
		return true;
	}
	private void connect() {
		try {
			socket = new Socket("localhost", 5000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
		} catch (IOException e) { System.err.println(e); }
	}
	
	public static void main(String[] args) {
		GUIClient a = new GUIClient();
		a.setVisible(true);
        a.setLocation(300,100);
		a.setSize(450,300);
		
		a.connect();
	}
}
