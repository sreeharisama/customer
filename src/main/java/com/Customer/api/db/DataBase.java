/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: DataBase.java
 * 
 * Description: DataBase: Base class to perform any database operations
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.api.db;

import java.util.List;
import java.util.Map;

import com.Customer.common.Global;
import com.Customer.common.Helper;
import com.core.DataAccessLayer;
import com.core.DatabaseConfig;

/**
 * Base class to perform any database operations
 */
public abstract class DataBase {

	// =============================================================================================================

	/**
	 * Constructor
	 */
	public DataBase() {
		establishDatabaseConnections();
	}

	// =============================================================================================================

	/**
	 * Gets single row as Map<String, Object> Object.
	 * 
	 * @param db
	 *            -> com.marketshare.constants.MicroServiceDB
	 * @param query
	 *            -> Database Query
	 * @return -> Map<String, Object>
	 */
	protected Map<String, Object> getSingleRowAsMap(String query) {
		try {
			return m_dbLayer.getSingleRow(query);
		} catch (Exception e) {
			Helper.appendErrorMessage(e.toString());
		}
		return null;
	}

	/**
	 * Gets Single Column with multiple rows as List of Strings.
	 * 
	 * @param db
	 *            -> com.marketshare.constants.MicroServiceDB
	 * @param query
	 *            -> Database Query
	 * @return -> List<String>
	 */
	protected List<String> getSingleColWithMultipleRowsList(String query) {
		try {
			return m_dbLayer.getRowsOfSingleColumnAsList(query);
		} catch (Exception e) {
			Helper.appendErrorMessage(e.toString());
		}
		return null;
	}
	// =============================================================================================================

	/**
	 * Validates All Database Connections are established or not
	 * 
	 * @return -> True/False
	 */
	public boolean isConnectionOpen() {
		try {
			return m_dbLayer.isConnectionOpen();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * Close all opened database connections
	 * 
	 * @return -> True/False
	 */
	public boolean closeConnection() {
		try {
			return m_dbLayer.closeConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	// =============================================================================================================

	/**
	 * Method to establish all Database connections
	 */
	private void establishDatabaseConnections() {
		setDBLayer(Global.getDBConfig());
	}

	/**
	 * Establish Application Information DatabaseLayer Object
	 * 
	 * @param databaseConfig
	 *            -> com.marketshare.core.util.DatabaseConfig
	 */
	private static void setDBLayer(DatabaseConfig databaseConfig) {
		if (m_dbLayer == null) {
			Helper.printLog("", "Application Info:");
		}
	}
	// =============================================================================================================

	/**
	 * Application Information DatabaseLayer Object
	 */
	private static DataAccessLayer m_dbLayer = null;

	// =============================================================================================================
}