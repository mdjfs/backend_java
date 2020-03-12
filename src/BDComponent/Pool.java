package BDComponent;

import java.util.ArrayList;

public class Pool {
	
	private static ArrayList<DBComponent> instances = new ArrayList<DBComponent>();
	private static int max_connections = 50;
	private static int hops = 5;
	private static int requests = 0;
	
	
	private Pool() {
		
	}
	
	public static synchronized DBComponent getDBInstance() {
		requests++;
		if(instances.size() < requests && requests < max_connections)
		{
			for(int i = 0; i < hops; i++)
			{
				instances.add(new DBComponent());
			}
		}
		for(DBComponent viewStatus : instances) {
			if(viewStatus.getBusy() == false) {
				viewStatus.setBusy(true);
				return viewStatus;
			}
		}
		return null;
	}
	
	public static synchronized void returnDBInstance(DBComponent instance) {
		instance.setBusy(false);
		requests--;
	}
}
