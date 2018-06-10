/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: Helper.java
 * 
 * Description:  Helper class supports common usage of methods.
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */
package com.Customer.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.SkipException;

/**
 * Helper class which contains re-usable methods
 */
public class Helper {

	// ---------------------------------------------------------------------------------------------------------------
	// Public Methods

	/**
	 * Method to append the error messages.
	 * 
	 * @param errorMessage
	 *            -> Error message to write the Global.TEST_CASE_ERROR_MESSAGES.
	 */
	public static void appendErrorMessage(String errorMessage) {
		if (Global.TEST_CASE_ERROR_MESSAGES != null) {
			Global.TEST_CASE_ERROR_MESSAGES.append(errorMessage);
			Global.TEST_CASE_ERROR_MESSAGES.append("<br />");
		}
		printLog(CLASS_NAME, errorMessage);
	}

	/**
	 * Method to compare two map objects
	 * 
	 * @param actual
	 * @param expected
	 * @param message
	 * @return
	 */
	public static boolean compareTwoMapObjects(Map<String, Object> actual, Map<String, Object> expected,
			String message) {
		if (actual.size() != expected.size()) {
			Global.TEST_CASE_ERROR_MESSAGES.append(String.format(
					"%s. Both map object size is not matched. Actual Map Object Size: %d. Expected Map Object Size: %d <br />",
					message, actual.size(), expected.size()));
			printLog(CLASS_NAME,
					String.format(
							"%s. Both map object size is not matched. Actual Map Object Size: %d. Expected Map Object Size: %d <br /> Actual: %s <br /> Expected: %s",
							message, actual.size(), expected.size(), actual.toString(), expected.toString()));
			return false;
		}

		List<Boolean> results = new ArrayList<Boolean>();
		for (Entry<String, Object> entry : actual.entrySet()) {
			Object aValue = entry.getValue() == null ? "" : entry.getValue().toString().trim();
			Object bValue = expected.get(entry.getKey()) == null ? "" : expected.get(entry.getKey()).toString().trim();

			if (!aValue.toString().equals(bValue)) {
				Global.TEST_CASE_ERROR_MESSAGES
						.append(String.format("%s. Actual:%s, Expected:%s <br />", message, actual, expected));
				results.add(false);
			} else {
				results.add(true);
			}
		}
		return results.contains(false) ? false : true;
	}

	/**
	 * Prints message in the console
	 * 
	 * @param message
	 */
	public static void printLog(String className, String message) {
		System.out.println("Error in class: " + className + "," + message);
	}

	/**
	 * Method to skip this particular exception
	 * 
	 * @param skipMessage
	 */
	public static void skipTestCase(String skipMessage) {
		throw new SkipException(skipMessage);
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Private methods

	/**
	 * get results from boolean list
	 */
	public static boolean returnResults(List<Boolean> results) {
		return results.contains(false) ? false : true;
	}

	/**
	 * Gets formatted date
	 * 
	 * @param format
	 * @return
	 */
	public static String getFormatedDate(String format) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	// =====================================================
	private static final String CLASS_NAME = Helper.class.getName();

}
