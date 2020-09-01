package com.api.testing;


import com.api.testing.listeners.CustomListener;
import com.api.testing.listeners.PriorityInterceptor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Listeners({ CustomListener.class , PriorityInterceptor.class})
public class TestCase extends BaseAPITest{
    public static Logger logger = LogManager.getLogger(TestCase.class);

    @BeforeSuite
    public static void printRemoteGridConsoleURL() throws Exception {

        try {
            InetAddress ip = InetAddress.getLocalHost();
            Reporter.log("Running on IP Address: " + ip,true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}

