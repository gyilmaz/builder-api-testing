package com.api.testing;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BaseAPITest {


    public static Logger logger = LogManager.getLogger(BaseAPITest.class);
    public static String TEST_MACHINE = System.getProperty("testmachine");

    public static String LIST_USERS= TEST_MACHINE+ "/api/users";
    public static String SINGLE_USER= TEST_MACHINE+ "/api/users/@@@id@@@";
    public static String CREATE_USER= TEST_MACHINE+ "/api/users";
    public static String UNKNOWN= TEST_MACHINE+"/api/unknown";

}
