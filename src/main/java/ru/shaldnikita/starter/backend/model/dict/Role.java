package ru.shaldnikita.starter.backend.model.dict;

public class Role {
	public static final String OPERATOR = "operator";
	public static final String ADMIN = "admin";

	private Role() {
	}

	public static String[] getAllRoles() {
		return new String[] { OPERATOR, ADMIN };
	}

}
