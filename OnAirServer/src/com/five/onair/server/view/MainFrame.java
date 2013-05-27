package com.five.onair.server.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextArea;

import com.five.onair.server.DiscoveryThread;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton btnStart;
	private Thread discoveryThread;
	private DatagramSocket socket;
	private JButton btnNewButton;
	private JButton btnInstalar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();

					frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);

		setTitle("On Air server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 200, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblServidorDeAplicativos = new JLabel("Servidor de aplicativos:");
		GridBagConstraints gbc_lblServidorDeAplicativos = new GridBagConstraints();
		gbc_lblServidorDeAplicativos.insets = new Insets(0, 0, 5, 5);
		gbc_lblServidorDeAplicativos.gridx = 0;
		gbc_lblServidorDeAplicativos.gridy = 0;
		contentPane.add(lblServidorDeAplicativos, gbc_lblServidorDeAplicativos);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!discoveryThread.isAlive()) {

					try{
						discoveryThread.start();
					}catch (IllegalThreadStateException ie) {
						try {
							socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
							socket.setBroadcast(true);
						} catch (SocketException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						discoveryThread=new DiscoveryThread(textArea, socket);
						discoveryThread.start();
						
					}
					btnStart.setText("Stop");

					appendToLog("Servidor iniciado");
				} else {
					discoveryThread.interrupt();
					socket.close();
					try {
						discoveryThread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					btnStart.setText("Start");

					appendToLog("Servidor finalizado");
				}

			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.anchor = GridBagConstraints.WEST;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 0;
		contentPane.add(btnStart, gbc_btnStart);
		
		btnNewButton = new JButton("Configurações...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigFrame frame=ConfigFrame.getInstance();
				frame.setVisible(true);
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		btnInstalar = new JButton("Instalar...");
		btnInstalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame frame=InstalledAppFrame.getInstance();
				frame.setVisible(true);
				
			}
		});
		GridBagConstraints gbc_btnInstalar = new GridBagConstraints();
		gbc_btnInstalar.anchor = GridBagConstraints.EAST;
		gbc_btnInstalar.insets = new Insets(0, 0, 5, 0);
		gbc_btnInstalar.gridx = 2;
		gbc_btnInstalar.gridy = 1;
		contentPane.add(btnInstalar, gbc_btnInstalar);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());  //give your JPanel a BorderLayout

		textArea = new JTextArea(); 
		JScrollPane scroll = new JScrollPane(textArea); //place the JTextArea in a scroll pane
		panel.add(scroll, BorderLayout.CENTER); //add the JScrollPane to the panel
		// CENTER will use up all available space

		

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 6, 0, 6);
		gbc_textArea.gridwidth = 3;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 2;
		contentPane.add(panel, gbc_textArea);

		
		
		try {
			socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		discoveryThread=new DiscoveryThread(textArea, socket);

	}

	public void appendToLog(String text) {

		if (textArea.getText().equals(""))
			textArea.setText(text);
		else {
			textArea.setText(textArea.getText() + "\n" + text);
		}

	}

}
