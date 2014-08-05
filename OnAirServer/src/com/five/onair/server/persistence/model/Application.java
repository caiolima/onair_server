package com.five.onair.server.persistence.model;

import net.sf.json.JSONObject;

public class Application {

	private String name;
	private String description;
	private String url;
	private String installedLocation="none";
	private String port;
	private String protocol;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInstalledLocation() {
		return installedLocation;
	}

	public void setInstalledLocation(String installedLocation) {
		this.installedLocation = installedLocation;
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public JSONObject getJSONFormat(){
		JSONObject object=new JSONObject();
	
		object.put("name", this.name);
		object.put("description", this.description);
		object.put("url", this.url);
		object.put("port", this.port);
		object.put("protocol", this.protocol);
		
		return object;
	}
	
}
