package com.core;

import java.util.ArrayList;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class ExecuteTestNGTests {
	@Test
	public TestListenerAdapter runTestClasses(String parallelMode, String suiteName, String testName,
			String... classes) {
		TestNG testNg = createXMLSuite(suiteName, testName, classes);
		testNg.setParallel(parallelMode);
		testNg.setThreadCount(10);
		testNg.setVerbose(2);
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		testNg.addListener(testListenerAdapter);
		testNg.run();
		return testListenerAdapter;
	}

	protected TestNG createXMLSuite(String suiteName, String testName, String... classes) {
		TestNG myTestNG = new TestNG();
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName(suiteName);
		XmlTest myTest = new XmlTest(mySuite);
		myTest.setName(testName);
		int index = 0;
		String[] myTests = classes;
		int arg8 = classes.length;

		for (int arg9 = 0; arg9 < arg8; ++arg9) {
			String c = myTests[arg9];
			XmlClass xc = new XmlClass(c, index++, true);
			myTest.getXmlClasses().add(xc);
		}

		ArrayList<XmlTest> arg12 = new ArrayList<XmlTest>();
		arg12.add(myTest);
		mySuite.setTests(arg12);
		myTestNG.setDefaultSuiteName(suiteName);
		myTestNG.setCommandLineSuite(mySuite);
		return myTestNG;
	}
}