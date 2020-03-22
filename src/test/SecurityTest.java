package test;

import java.sql.SQLException;

import helpers.Security;

public class SecurityTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Security securityobject = new Security();
		securityobject.chargePermissions();

	}

}
