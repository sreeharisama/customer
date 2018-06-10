
/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: Global.java
 * 
 * Description:  Global class which will hold all information required for automation scripts.
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */
package com.Customer.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.core.DatabaseConfig;

/**
 * Class which holds all basic information which will used across all APIs
 */
public class Global {

	// -----------------------------------------------------------------------------------------------------------
	// Public Methods

	/**
	 * Method to build the property files
	 */
	public static boolean buildPropertyFiles() {
		try {
			m_appProperties = getProperties(APP_PROPERTIES);
			setConfigDetails();
			setClientURL(getClientConfigDetailsByKey(RESTBASEURL));
			return true;
		} catch (Exception e) {
			Helper.printLog("", e.toString());
		}
		return false;
	}

	/**
	 * Set Client URL
	 * 
	 * @param clientURL
	 */
	public static void setClientURL(String clientURL) {
		m_clientURL = clientURL.replace("https:", "http:").trim();
	}

	/**
	 * Gets ClientURL
	 * 
	 * @return -> Client URL
	 */
	public static String getClientURL() {
		return m_clientURL;
	}

	/**
	 * Gets Action base URL
	 */
	public static String getBaseUrl() {
		return String.format("%s/api/", Global.getClientURL());
	}

	/**
	 * Get basic query parameters
	 */
	public static Map<String, Object> getBasicQueryParams() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.putAll(m_basicQueryParams);
		return params;
	}

	/**
	 * Method to get client specific values by key
	 */
	public static String getClientConfigDetailsByKey(String key) {
		return m_configDetails.get(key);
	}

	/**
	 * Gets Database configuration settings
	 */
	public static DatabaseConfig getDBConfig() {
		if (m_databaseConfig == null)
			setDatabaseConfig();
		return m_databaseConfig;
	}

	/**
	 * Sets basic query parameters
	 */
	public static void setBasiQueryParams(String userId, String sessionId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userid", userId);
		params.put("sid", sessionId);
		params.put("language", getValueFromAppProperty(LANGUAGE));
		m_basicQueryParams = params;
	}

	/**
	 * Gets Test Suites which need to execute
	 */
	public static String getTestSuites() {
		return getValueFromAppProperty(TEST_SUITES);
	}

	// -----------------------------------------------------------------------------------------------------------
	// Private methods
	/**
	 * Method to get value from APP Property file
	 */
	private static String getValueFromAppProperty(String key) {
		return m_appProperties.getProperty(key);
	}

	/**
	 * Method to set Database configuration settings
	 */
	private static void setDatabaseConfig() {
		try {
			DatabaseConfig databaseConfig = new DatabaseConfig();
			String datasourceurl = getClientConfigDetailsByKey(DATASOURCEURL);
			String databasename = datasourceurl.split(":")[5];
			String databasetype = datasourceurl.split(":")[1];
			String hostname = datasourceurl.split(":")[3].replace("@", "");
			String port = datasourceurl.split(":")[4];
			databaseConfig.setDatabaseName(databasename);
			databaseConfig.setDatabaseType(databasetype);
			databaseConfig.setHostName(hostname);
			databaseConfig.setPassword(getClientConfigDetailsByKey(DATASOURCEPASSWORD));
			databaseConfig.setPort(port);
			databaseConfig.setUserName(getClientConfigDetailsByKey(DATASOURCEUSERNAME));

			m_databaseConfig = databaseConfig;
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME, e.toString());
		}
	}

	/**
	 * Method to set client config details from properties
	 */
	private static void setConfigDetails() {
		Set<Object> appKeys = m_clientConfigDetails.keySet();
		for (Object eachKey : appKeys) {
			m_configDetails.put(eachKey.toString(), m_clientConfigDetails.getProperty(eachKey.toString()));
		}
	}

	public static Properties getProperties(String fileName) throws IOException {
		return getPropertyFileDetails(fileName);
	}

	private static Properties getPropertyFileDetails(String fileName) throws IOException {
		InputStream inputStream = null;
		Properties properties = new Properties();

		Properties arg3;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (inputStream == null) {
				return properties;
			}

			properties.load(inputStream);
			Properties e = properties;
			return e;
		} catch (Exception arg7) {
			arg7.printStackTrace();
			arg3 = properties;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}

		return arg3;
	}

	// -----------------------------------------------------------------------------------------------------------
	// public Static variables
	public static StringBuilder TEST_CASE_ERROR_MESSAGES = null;
	// ----------------------------------------------------------------------------
	// Private variables
	private static final String TEST_SUITES = "testsuites";
	private static final String DATASOURCEUSERNAME = "dataSourceUsername";
	private static final String DATASOURCEPASSWORD = "dataSourcePassword";
	private static final String DATASOURCEURL = "dataSourceUrl";
	private static final String LANGUAGE = "language";
	private static final String RESTBASEURL = "restapibaseurl";
	/**
	 * Holds basic query parameters
	 */
	private static Map<String, Object> m_basicQueryParams = null;

	/**
	 * Holds database configuration settings
	 */
	private static DatabaseConfig m_databaseConfig = null;

	/**
	 * Holds Client config details
	 */
	private static Properties m_clientConfigDetails = null;

	private static Map<String, String> m_configDetails = new HashMap<String, String>();

	/**
	 * Holds Class name
	 */
	private final static String CLASS_NAME = Global.class.getName();

	private final static String APP_PROPERTIES = "app.properties";
	/**
	 * Client URL
	 */
	private static String m_clientURL = null;
	/**
	 * Holds the APP property values
	 */
	private static Properties m_appProperties = null;

}
