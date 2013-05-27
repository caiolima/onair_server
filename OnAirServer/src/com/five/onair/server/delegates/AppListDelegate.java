package com.five.onair.server.delegates;

import com.five.onair.server.persistence.model.Application;
import com.five.onair.server.persistence.model.ApplicationList;

public interface AppListDelegate {

	public void applicationInstalled(ApplicationList appList,Application app);
	public void applicationRemoved(ApplicationList appList, Application app); 
	
}
