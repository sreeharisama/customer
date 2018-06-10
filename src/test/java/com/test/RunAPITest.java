/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: RunAPITest.java
 * 
 * Description:  Class to run API automation test suites using TestNG.  
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Customer.common.Global;
import com.Customer.common.Helper;
import com.Customer.managers.DBManager;
import com.Customer.managers.TestNGManager;

/**
 * Class to run all test suites of 360 Digital API automation
 */
@Test
public class RunAPITest {

	/**
	 * Variable to hold all results details, and will be used to generate custom
	 * report
	 */
	LinkedHashSet<TestListenerAdapter> listeners = new LinkedHashSet<TestListenerAdapter>();
	private static String m_testExecutionTime = null;

	// ------------------------------------------------------------------------------------------------------------------------
	// Configuration Settings

	/**
	 * Configuration setting before start test suites. We will validate database
	 * connection is established or not.
	 */
	@BeforeClass
	public void beforeClass() {
		m_testExecutionTime = Helper.getFormatedDate("yyyy-MM-dd'T'hh:mm:ssZ").replace("'", "");

		Helper.printLog(CLASS_NAME,
				"------------------------------------------------------------------------------------------------------------------------");
		Helper.printLog("", "API Automation basic setup started:" + m_testExecutionTime);
		Helper.printLog(CLASS_NAME,
				"------------------------------------------------------------------------------------------------------------------------");
		if (!verifyAndSetBasicSettings()) {
			Assert.assertTrue(false, "Basic setup is failed.");
		}

		Helper.printLog(CLASS_NAME,
				"------------------------------------------------------------------------------------------------------------------------");
		Helper.printLog(CLASS_NAME,
				"Basic validation and login API call is successful. Test suites execution started @ "
						+ Helper.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ") + " ....");
		Helper.printLog(CLASS_NAME,
				"------------------------------------------------------------------------------------------------------------------------");
	}

	/**
	 * Once all test suites execution is done then we will close the database
	 * connection, logout and generate report.
	 */
	@AfterClass
	public void afterClass() {
		if (!validateToProceedTestRuns()) {
			return;
		}
		try {
			// Here can write methods to generate report
		} catch (Exception e) {
			Helper.printLog("", e.toString());
		} finally {
			DBManager.CustomerDB().closeConnection();
		}
		Helper.printLog(CLASS_NAME,
				"Test suites execution completed @ " + Helper.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ") + " ...");
		Helper.printLog(CLASS_NAME,
				"------------------------------------------------------------------------------------------------------------------------");
	}

	// ------------------------------------------------------------------------------------------------------------------------
	// Test Suites

	/**
	 * Executes test suites based on settings
	 */
	@Test(priority = 1, enabled = true)
	public void TestSuites() {
		String[] testSuites = Global.getTestSuites().split(",");
		if (testSuites[0].trim().toLowerCase().equals("all")) {
			executeTestSuites(getAllTestSuites(), true);
		} else {
			executeTestSuites(testSuites, false);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------
	// Private methods

	/**
	 * Method to execute test suites
	 */
	private void executeTestSuites(String[] testSuites, boolean isAll) {
		for (String testSuite : testSuites) {
			if (isAll)
				testSuite = testSuite.replace("ts_", "").replace(".properties", "");
			try {
				String[] testCases = getTestClassesToRun(String.format("ts_%s", testSuite.toLowerCase().trim()));
				if (testCases.length > 0) {
					TestListenerAdapter testListenerAdapter = TestNGManager.getTestNGInstance().runTestClasses("NONE",
							testSuite.toUpperCase(), testSuite.toUpperCase(), testCases);
					listeners.add(testListenerAdapter);
				}
			} catch (Exception e) {
				Helper.printLog(CLASS_NAME, e.getMessage());
			}
		}
	}

	/**
	 * Method to get all test suites
	 */
	private String[] getAllTestSuites() {
		String value = Thread.currentThread().getContextClassLoader().getResource("app.properties").getPath();
		List<String> files = new ArrayList<String>();
		File dir = new File(value.substring(0, value.lastIndexOf("/")));
		for (File nextFile : dir.listFiles()) {
			if (nextFile.isFile())
				if (nextFile.getName().startsWith("ts_")) {
					files.add(nextFile.getName());
				}
		}
		return files.toArray(new String[files.size()]);
	}

	/**
	 * Verifies database connection is established or not
	 */
	private boolean validateDatabaseConnection() {
		if (DBManager.CustomerDB().isConnectionOpen()) {
			return true;
		}
		Helper.printLog(CLASS_NAME, "Not able to login to database. Please verify database connection details");
		return false;
	}

	/**
	 * Validate to run all test cases only after successful login
	 */
	private boolean validateToProceedTestRuns() {
		return m_finalStatusToRunTests;
	}

	/**
	 * Method to verify database connection, login status and set basic settings
	 */
	private boolean verifyAndSetBasicSettings() {

		m_finalStatusToRunTests = false;
		try {
			if (!Global.buildPropertyFiles()) {
				return false;
			}
			Helper.printLog("", "Properties build sucessfully....");
			Helper.printLog(CLASS_NAME,
					"------------------------------------------------------------------------------------------------------------------------");

			if (!validateDatabaseConnection()) {
				return false;
			}
			Helper.printLog("", "Database Connection sucessfully completed...");
			Helper.printLog(CLASS_NAME,
					"------------------------------------------------------------------------------------------------------------------------");
			m_finalStatusToRunTests = true;
			return true;
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME, e.toString());
		}
		return false;
	}

	/**
	 * Variable to holds the status to run test suites
	 */
	private boolean m_finalStatusToRunTests = false;

	/**
	 * Gets all the test classes to run
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String[] getTestClassesToRun(String fileName) throws IOException {
		ArrayList<?> testClassNames = new ArrayList<Object>();
		Properties testClasses = Global.getProperties(String.format("%s.properties", new Object[] { fileName }));
		if (testClasses != null) {
			if (testClasses.containsKey("All")) {
				if (testClasses.get("All").toString().trim().toLowerCase().equals("yes")) {
					testClasses.remove("All");
					return (String[]) testClasses.keySet().toArray(new String[testClasses.keySet().size()]);
				}

				testClasses.remove("All");
			}
		}
		return (String[]) testClassNames.toArray(new String[testClassNames.size()]);
	}

	// Constant variable
	private final static String CLASS_NAME = RunAPITest.class.getName();
}