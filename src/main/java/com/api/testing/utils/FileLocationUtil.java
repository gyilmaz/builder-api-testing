package com.api.testing.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.api.testing.utils.Driver.logger;

public class FileLocationUtil {


    private static String resourceFileLocation="src/test/resources/";


    public static String getFileLocation(String fileName) throws Exception{

        String cwd = System.getProperty("user.dir");
        logger.info("Current working directory : " + cwd);
        Path pathNew = Paths.get(cwd+"/src/test/resources/", fileName);
        return pathNew.toString();
    }

}
