package com.five.onair.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;


public class ListenThread extends Thread {
		
	@Override
	public void interrupt() {

		interrupted = true;
	}

	private DatagramSocket socket;
	private static JTextArea out;
	private boolean interrupted = false;
	private static int numberThreadsCreated = 0; // thread's count
	private static final int numberThreadsMax = 20;

	public ListenThread(JTextArea out, DatagramSocket socket) {
		ListenThread.out = out;
		this.socket=socket;
	}
	
	public static void incrementThreadCount(){
		numberThreadsCreated++;
	}
	
	public static void decrementThreadCount(){
		numberThreadsCreated--;
	}

	@Override
	public void run() {
		try {
			// Keep a socket open to listen to all the UDP trafic that is
			// destined for this port
			
			while (true) {
				try {
					if (interrupted)
						break;
					appendToLog(getClass().getName()
							+ ">>>Ready to receive broadcast packets!");

					// Receive a packet
					byte[] recvBuf = new byte[15000];
					DatagramPacket packet = new DatagramPacket(recvBuf,
							recvBuf.length);
					socket.receive(packet);

					// Send a response
					if(numberThreadsCreated < numberThreadsMax)
						new ResponseThread(packet).start();
						
					
				} catch (Exception e) {
					
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void appendToLog(String text) {
		out.setText(out.getText() + "\n" + text);
	}


}
