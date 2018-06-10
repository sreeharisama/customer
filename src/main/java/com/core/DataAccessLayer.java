package com.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public abstract class DataAccessLayer {
	protected Connection m_connection;

	public DataAccessLayer(DatabaseConfig databaseConfig) {
		this(databaseConfig.getHostName(), databaseConfig.getPort(), databaseConfig.getDatabaseName(),
				databaseConfig.getUserName(), databaseConfig.getPassword(), databaseConfig.getDatabaseType());
	}

	public DataAccessLayer(String hostName, String portNumber, String databaseName, String userName, String password,
			String databaseType) {
		this.m_connection = null;
		this.setDatabaseConnection(hostName, portNumber, databaseName, userName, password, databaseType);
	}

	public abstract boolean compareTwoResultSets(String arg0, String arg1, String arg2);

	public abstract boolean executeQuery(String arg0);

	public abstract boolean executeUpdate(String arg0);

	public abstract boolean executeInsert(String arg0);

	public abstract boolean executeDelete(String arg0);

	public abstract String getColumnValue(String arg0, String arg1);

	public abstract Map<String, Object> getSingleRow(String arg0);

	public abstract LinkedHashSet<LinkedHashMap<String, Object>> getMultipleRows(String arg0);

	public abstract Map<String, String> getTwoColumnsWithMultipleRows(String arg0);

	public abstract Map<String, String> getTwoColumnsWithMultipleRowsWithClob(String arg0);

	public abstract List<String> getRowsOfSingleColumnAsList(String arg0);

	public abstract boolean closeConnection();

	public abstract boolean isConnectionOpen();

	public abstract List<String> getColumnNamesFromTable(String arg0);

	public abstract boolean executeStoredProcedureWithOnlyOutParameters(String arg0, Map<String, Integer> arg1);

	public abstract String executeStoredProcedureWithOnlyOutParameters(String arg0, Map<String, Integer> arg1,
			String arg2);

	public abstract boolean executeDMLQueries(String arg0);

	public abstract List<String> processHiveDML(String arg0);

	public abstract boolean processHiveDDL(String arg0);

	public abstract boolean convertResultSetToCSV(String arg0, String arg1);

	private boolean setDatabaseConnection(String hostName, String portNumber, String databaseName, String userName,
			String password, String databaseType) {
		String errorMessage;
		try {
			errorMessage = String.format("jdbc:oracle:thin:@%s:%s:%s",
					new Object[] { hostName, portNumber, databaseName });
			this.m_connection = DriverManager.getConnection(errorMessage, userName, password);
			System.out.println(String.format("%s connection established successfully",
					new Object[] { databaseType.toUpperCase() }));
			return true;
		} catch (SQLException arg9) {
			System.out.println(arg9.getMessage());
		} catch (Exception arg10) {
			System.out.println(arg10.getMessage());
		}

		errorMessage = String.format("%s connection not established.", new Object[] { databaseType.toUpperCase() });
		System.out.println(errorMessage);
		return false;
	}
}