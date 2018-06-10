package com.core;

public class DatabaseConfig {
	private String m_databaseName;
	private String m_databaseType;
	private String m_hostName;
	private String m_password;
	private String m_port;
	private String m_userName;

	public String getDatabaseName() {
		return this.m_databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.m_databaseName = databaseName;
	}

	public String getDatabaseType() {
		return this.m_databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.m_databaseType = databaseType;
	}

	public String getHostName() {
		return this.m_hostName;
	}

	public void setHostName(String hostName) {
		this.m_hostName = hostName;
	}

	public String getPassword() {
		return this.m_password;
	}

	public void setPassword(String password) {
		this.m_password = password;
	}

	public String getPort() {
		return this.m_port;
	}

	public void setPort(String port) {
		this.m_port = port;
	}

	public String getUserName() {
		return this.m_userName;
	}

	public void setUserName(String userName) {
		this.m_userName = userName;
	}
}