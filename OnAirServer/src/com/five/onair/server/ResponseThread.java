package com.five.onair.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import net.sf.json.JSONObject;

import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.Configurations;


public class ResponseThread extends Thread{
	
	private DatagramPacket packet;
	private Configurations config;

	
	public ResponseThread(DatagramPacket receivePacket){
		this.packet = receivePacket;
		this.config=Configurations.getInstance();

		ListenThread.incrementThreadCount();
	}

	@Override
	public void run() {
		
		// Packet received
		ListenThread.appendToLog(getClass().getName()
				+ ">>>Discovery packet received from: "
				+ packet.getAddress().getHostAddress());
		ListenThread.appendToLog(getClass().getName()
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
			
			DatagramPacket sendPacket = new DatagramPacket(
					sendData, sendData.length, packet.getAddress(),
					packet.getPort());
			try {
				new DatagramSocket().send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ListenThread.appendToLog(getClass().getName()
					+ ">>>Sent packet to: "
					+ sendPacket.getAddress().getHostAddress());
			
		}
		
		ListenThread.decrementThreadCount();

	}
	
}
