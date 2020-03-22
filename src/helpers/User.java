package helpers;

public class User {
	private int id_profile = 1; // GUEST por defecto
	
	public void setProfile(int id_profile) {
		this.id_profile = id_profile;
	}
	
	public int getIDProfile() {
		return id_profile;
	}
	
}
