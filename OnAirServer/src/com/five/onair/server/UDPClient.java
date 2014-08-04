package com.five.onair.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class UDPClient {
	
	public static void connect(){
		try {
			// Open a random port to send the package
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);
			
			InetAddress broadCast = null;

			byte[] sendData = "DISCOVER_AIR_SERVER".getBytes();
			
			System.setProperty("java.net.preferIPv4Stack", "true");
			for (Enumeration<NetworkInterface> niEnum = NetworkInterface
					.getNetworkInterfaces(); niEnum.hasMoreElements();) {
				NetworkInterface ni = niEnum.nextElement();
				if (!ni.isLoopback()) {
					for (InterfaceAddress interfaceAddress : ni
							.getInterfaceAddresses()) {

						broadCast = interfaceAddress.getBroadcast();
					}
				}
			}

			
			try {
				/*DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, broadCast, 8888);*/
				//System.out.println(broadCast.getHostAddress());
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length,Inet4Address.getByName("0.0.0.0"), 8888);
				c.send(sendPacket);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Wait for a response
			byte[] recvBuf = new byte[500000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf,
					recvBuf.length);
			c.setSoTimeout(8000);
			c.receive(receivePacket);

			// Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			System.out.println(message);

			// Close the port!
			c.close();
		} catch (IOException ex) {

		}
	}
	
	public static void main(String args[]) throws Exception {
		
		connect();
		
		

	}
	
}