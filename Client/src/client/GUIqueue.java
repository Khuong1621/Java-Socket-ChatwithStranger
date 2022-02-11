package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;

public class GUIqueue extends JFrame {

	private Socket socket;
	private JPanel contentPane;
	private JTextField txtangChMt;
	public BufferedReader in;
	public PrintWriter out;
	private String name="";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUIqueue frame = new GUIqueue();
//					frame.setVisible(true);
//					
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//

/**
 * Create the frame.
 */	
	public GUIqueue(Socket socket, String name) {
		this.name = name;
		setTitle(name);
		this.socket = socket;
		try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
	
		} catch(Exception e) {
			System.out.println();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hi, "+name);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel.setBounds(150, 24, 128, 59);
		contentPane.add(lblNewLabel);
//		
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("alo");
				out.println("/find");
				while(true) {
					try {
						String namerep = in.readLine();
						if(namerep.equals("/outofGuest")) {
//							System.out.println("het nguoi trong pcho");
							break;
						}
						int kqrep = JOptionPane.showConfirmDialog(contentPane, "Có "+ namerep +" muốn kết nối, vào luôn nhé!!","Thông báo",JOptionPane.YES_NO_OPTION);
						if(kqrep == JOptionPane.YES_OPTION) {
							out.println("/agreetoconnect");
							GUIchat chat = new GUIchat(name, socket, namerep);
							dispose();
							chat.setVisible(true);
//							System.out.println("dong y ket noi");
							return;
						} else {
							out.println("/refuse");
						}
					} catch(Exception ex) {
						System.out.println(ex);
					}
				}
				
				Thread wait = new Thread() {
					@Override
					public void run() {
						try {
							//System.out.println("Cho ket noi");
							String res = in.readLine();
							
							if(res.indexOf("/connect") != -1) {
								String nameGuest = res.replace("/connect", "");
								JOptionPane.showMessageDialog(contentPane, nameGuest + " đã kết nối với bạn");
								out.println("/startChat"+nameGuest);
								GUIchat chat = new GUIchat(name, socket, nameGuest);
								dispose();
								chat.setVisible(true);
								return;
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
			
	

//		
		txtangChMt = new JTextField();
		txtangChMt.setEditable(false);
		txtangChMt.setBackground(Color.PINK);
		txtangChMt.setFont(new Font("Segoe UI", Font.BOLD, 14));
		txtangChMt.setText("\u0110ang ch\u1EDD m\u1ED9t ng\u01B0\u1EDDi d\u00F9ng kh\u00E1c k\u1EBFt n\u1ED1i...");
		txtangChMt.setHorizontalAlignment(SwingConstants.CENTER);
		txtangChMt.setBounds(73, 120, 289, 29);
		txtangChMt.setBorder(null);
		contentPane.add(txtangChMt);
		txtangChMt.setColumns(10);
	}
}
