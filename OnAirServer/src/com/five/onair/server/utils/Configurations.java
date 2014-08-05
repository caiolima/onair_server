package com.five.onair.server.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Configurations {

	private static Configurations singleton;

	private String serverIP;
	private String serverPath;

	public static Configurations getInstance() {
		if (singleton == null)
			singleton = new Configurations();

		return singleton;
	}

	private Configurations() {
		this.serverIP = getLocalIpAddress();

		File config = new File("air.config");
		if (config.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						config));
				String linha;
				StringBuffer bufSaida = new StringBuffer();

				while ((linha = reader.readLine()) != null) {
					bufSaida.append(linha);
				}
				reader.close();

				this.serverPath = bufSaida.toString();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			}

		} else {
			this.serverPath="";
		}
	}

	public String getLocalIpAddress() {
		 String ipAddress = null;
		    Enumeration<NetworkInterface> net = null;
		    try {
		        net = NetworkInterface.getNetworkInterfaces();
		    } catch (SocketException e) {
		        throw new RuntimeException(e);
		    }

		    while(net.hasMoreElements()){
		        NetworkInterface element = net.nextElement();
		        Enumeration<InetAddress> addresses = element.getInetAddresses();
		        while (addresses.hasMoreElements()){
		            InetAddress ip = addresses.nextElement();
		            if (ip instanceof Inet4Address){

		                if (ip.isSiteLocalAddress()){

		                    ipAddress = ip.getHostAddress();
		                }

		            }

		        }
		    }
		    return ipAddress;

	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void save() {
		try {
			File config = new File("air.config");
			if (!config.exists()) {

				config.createNewFile();

			} else {
				config.delete();
				config.createNewFile();
			}
			
			FileWriter writer=new FileWriter(config);
			writer.append(serverPath);

			writer.close();
			
		} catch (IOException e) {

		}

	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

}
