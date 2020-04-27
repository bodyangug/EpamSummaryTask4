package ua.nure.aseev.SummaryTask4.db;

import ua.nure.aseev.SummaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author B.Aseev
 * 
 */
public enum Role {
	MANAGER, CLIENT;

	public static Role getRole(User user) {
		int roleId = user.getRole();
		return Role.values()[roleId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
