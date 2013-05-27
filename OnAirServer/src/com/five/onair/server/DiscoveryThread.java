package com.five.onair.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

import net.sf.json.JSONObject;

import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.Configurations;

public class DiscoveryThread extends Thread {
	
	private Configurations config;
	
	@Override
	public void interrupt() {

		interrupted = true;
	}

	private DatagramSocket socket;
	private JTextArea out;
	private boolean interrupted = false;

	public DiscoveryThread(JTextArea out, DatagramSocket socket) {
		this.out = out;
		this.socket=socket;
		this.config=Configurations.getInstance();
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

					// Packet received
					appendToLog(getClass().getName()
							+ ">>>Discovery packet received from: "
							+ packet.getAddress().getHostAddress());
					appendToLog(getClass().getName()
							+ ">>>Packet received; data: "
							+ new String(packet.getData()));

					// See if the packet holds the right command (message)
					String message = new String(packet.getData()).trim();
					if (message.equals("DISCOVER_AIR_SERVER")) {
						JSONObject object=new JSONObject();
						object.put("ip_server", config.getServerIP());
						object.put("applications", ApplicationList.getInstance().
								getListAsJSON());
						
						byte[] sendData = object.toString()
								.getBytes();

						// Send a response
						DatagramPacket sendPacket = new DatagramPacket(
								sendData, sendData.length, packet.getAddress(),
								packet.getPort());
						socket.send(sendPacket);

						appendToLog(getClass().getName()
								+ ">>>Sent packet to: "
								+ sendPacket.getAddress().getHostAddress());
					}
				} catch (Exception e) {
					
				}
			}
		} catch (Exception ex) {
			Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public void appendToLog(String text) {
		out.setText(out.getText() + "\n" + text);
	}

}
