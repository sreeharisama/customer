package com.Customer.managers;

import com.core.ExecuteTestNGTests;

public class TestNGManager {
	private static volatile ExecuteTestNGTests m_testNGInstance = null;

	public static ExecuteTestNGTests getTestNGInstance() {
		if (m_testNGInstance == null) {
			synchronized (ExecuteTestNGTests.class) {
				if (m_testNGInstance == null) {
					m_testNGInstance = new ExecuteTestNGTests();
				}
			}
		}
		return m_testNGInstance;
	}
}