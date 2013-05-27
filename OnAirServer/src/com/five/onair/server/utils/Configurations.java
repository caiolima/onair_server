package com.five.onair.server.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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

	public static String getLocalIpAddress() {
		try {
			InetAddress addr = InetAddress.getLocalHost();

			String hostAdress = addr.getHostAddress();

			// Get hostname
			return hostAdress;
		} catch (UnknownHostException e) {
		}

		return null;

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
