package com.spring.auth.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Profiles {

	public static final String BASIC_AUTH = "basic_auth";
	public static final String JWT_AUTH = "jwt_auth";
	public static final String OATH2_AUTH = "oauth2_auth";
	public static final String NO_AUTH = "no_auth";

}