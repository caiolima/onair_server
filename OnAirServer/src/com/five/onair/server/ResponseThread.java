package com.five.onair.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import net.sf.json.JSONObject;

import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.Configurations;


public class ResponseThread extends Thread{
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Configurations config;

	
	public ResponseThread(DatagramPacket receivePacket, DatagramSocket socket){
		this.socket = socket;
		this.packet = receivePacket;
		this.config=Configurations.getInstance();

		UDPServer.incrementThreadCount();
	}

	@Override
	public void run() {
		
		// Packet received
		UDPServer.appendToLog(getClass().getName()
				+ ">>>Discovery packet received from: "
				+ packet.getAddress().getHostAddress());
		UDPServer.appendToLog(getClass().getName()
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
				socket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UDPServer.appendToLog(getClass().getName()
					+ ">>>Sent packet to: "
					+ sendPacket.getAddress().getHostAddress());
			
			UDPServer.decrementThreadCount();
		}
		
		
	}
	
}
