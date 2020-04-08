package properties;


public class Properties {
	public static String NameProject = Properties.class.getProtectionDomain().getCodeSource().getLocation().getPath().split("/WEB-INF")[0].split("apps/")[1];
	public static String URI = Properties.class.getProtectionDomain().getCodeSource().getLocation().getPath().split(".metadata")[0] + NameProject + "/";
	public static String BussinesObjectsURI = URI + "src/bussinessObjects";
	public static String QuerysURI = URI + "config/config_querys.properties";
	public static String DBConfigURI = URI + "config/config_db.properties";
	public static String POOLConfigURI = URI + "config/config_pool.properties";
	
}
