package helpers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class QueryHandler {
	private ConfigComponent config_querys = new ConfigComponent("/home/mdjfs/Documentos/querys_config.properties");
	private Map<String, String> querys = new HashMap<String, String>();
	private Properties read_querys = null;
	
	public QueryHandler() {
		read_querys = config_querys.getObjectProperties();
		Enumeration<?> count_keys = read_querys.keys();
		while(count_keys.hasMoreElements()) {
			String next_element = count_keys.nextElement().toString();
			querys.put(next_element, read_querys.getProperty(next_element));
		}
	}
	
	public String GetQuery(String id) {
		return querys.get(id);
	}

}
