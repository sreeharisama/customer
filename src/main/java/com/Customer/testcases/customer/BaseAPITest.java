package com.Customer.testcases.customer;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.Customer.common.Global;
import com.Customer.common.Helper;

/**
 * Base class for base API test
 */
public abstract class BaseAPITest {

	/**
	 * Base Constructor
	 */
	public BaseAPITest(String testClassName, String testCaseName, String testCaseDescription) {
		m_currentTestClassName = testClassName;
		m_currentTestCaseDescription = testCaseDescription;
		m_currentTestCaseName = testCaseName;
		m_isTestPassed = false;
	}

	/**
	 * Base Constructor
	 */
	public BaseAPITest(Class<?> className, String testCaseDescription) {
		m_currentTestClassName = className.getName();
		m_currentTestCaseName = className.getSimpleName();
		m_currentTestCaseDescription = testCaseDescription;
		m_isTestPassed = false;
	}

	/**
	 * Method to run before test class
	 */
	@BeforeClass
	public void beforeClass() {
		m_logMessage = new StringBuilder();
		m_logMessage.append(String.format("TestCase - %s, started @ %s \n", m_currentTestCaseName,
				Helper.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ")));
		m_logMessage.append(
				"----------------------------------------------------------------------------------------------- \n");
		m_logMessage.append(String.format("Description: %s. \n", m_currentTestCaseDescription));
		Helper.printLog(m_currentTestClassName, m_logMessage.toString());
	}

	/**
	 * Method to run steps after test class
	 */
	@AfterClass
	public void afterClass() {
		m_logMessage = new StringBuilder();
		m_logMessage.append(String.format("TestCase - %s execution completed", m_currentTestCaseName)
				+ getStatusMessage() + "@ " + Helper.getFormatedDate("yyyy-MM-dd HH:mm:ss.SSS ") + "\n");
		m_logMessage.append(
				"-----------------------------------------------------------------------------------------------");
		Helper.printLog(m_currentTestClassName, m_logMessage.toString());
	}

	/**
	 * Get test error messages
	 */
	protected String getErrorMessage() {
		return (Global.TEST_CASE_ERROR_MESSAGES != null)
				? String.format("Error Information: %s", Global.TEST_CASE_ERROR_MESSAGES.toString()) : "";
	}

	// --------------------------------------------------------------------------------------------
	// Private methods

	/**
	 * Method to bind Status
	 */
	private String getStatusMessage() {
		return (m_isTestPassed == true) ? " successfully..." : " with failures...";
	}

	// Variables
	private String m_currentTestClassName = null;
	private String m_currentTestCaseName = null;
	private String m_currentTestCaseDescription = null;
	private StringBuilder m_logMessage = null;
	protected boolean m_isTestPassed = false;
}