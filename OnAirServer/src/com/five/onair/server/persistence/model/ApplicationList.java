package com.five.onair.server.persistence.model;

import java.util.Vector;

import net.sf.json.JSONArray;

import com.five.onair.server.delegates.AppListDelegate;
import com.five.onair.server.persistence.AppListXMLManager;

public class ApplicationList {

	private Vector<Application> applications;
	private static ApplicationList singleton;
	private Vector<AppListDelegate> listeners=new Vector<AppListDelegate>();
	
	public static ApplicationList getInstance(){
		if(singleton==null)
			singleton=new ApplicationList();
		
		return singleton;
	}
	
	private ApplicationList(){
		
		applications=AppListXMLManager.loadInstalledApplications();
		
	}
	
	public void installApp(Application app){
		applications.add(app);
		persistApps();
		for(AppListDelegate listener:listeners){
			listener.applicationInstalled(this, app);
		}
	}
	
	public void desinstallApp(Application app){
		applications.remove(app);
		persistApps();
		for(AppListDelegate listener:listeners){
			listener.applicationRemoved(this, app);
		}
	}
	
	private void persistApps(){
		AppListXMLManager.saveApplications(applications);
	}
	
	public int size(){
		return applications.size();
	}
	
	public Application get(int pos){
		return applications.get(pos);
	}
	
	public void registerToDelegate(AppListDelegate list){
		listeners.add(list);
	}
	
	public void removeToDelegate(AppListDelegate list){
		listeners.remove(list);
	}
	
	public JSONArray getListAsJSON(){
		
		JSONArray array=new JSONArray();
		
		for(Application app:applications)
			array.add(app.getJSONFormat());
		
		return array;
		
		
	}
	
	
}
