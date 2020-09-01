package com.api.testing.requests;

import com.api.testing.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetRequest extends BaseAPITest {

    public static Response getResponse(String location) {
        RestAssured.baseURI = location;
        logger.info("URL is " + location);
        return given().accept(ContentType.JSON).
//                log().all().
                when().
                get();
    }

    public static ValidatableResponse getValidatableResponse(String location) {
        RestAssured.baseURI = location;
        logger.info("URL " + location);

        return RestAssured.given().
//                log().all().
                when().
                get().
                then();
    }

    public static Object getObjectById(String location, Class clazz) {
        ValidatableResponse response = getValidatableResponse(location);
        Assert.assertTrue(response.extract().statusCode() == 200, "Status code is " + response.extract().statusCode());
      Object obj = null;
        try {
            Class c = Class.forName(clazz.getName());
            obj = response.extract().body().as(c);

        } catch (ClassNotFoundException e) {
            logger.error(e.getException());
        }
        return obj;
    }

    public static List getDefaultObjectList(String location, Class clazz, String jsonPath) {
        ValidatableResponse response = getValidatableResponse(location);
        logger.info(response.extract().asString());
        Assert.assertTrue(response.extract().statusCode() == 200, "Status code is " + response.extract().statusCode());
        List<Object> vulnList = response.extract().path(jsonPath);
        logger.info("size of the vuln list " + vulnList.size());
        List list = new ArrayList<>();
        Object obj = null;
        try {
            Class c = Class.forName(clazz.getName());
            for (int i = 0; i < vulnList.size(); i++) {
                obj = response.extract().response().getBody().jsonPath().getObject(jsonPath + "[" + i + "]", c);
                list.add(obj);
            }
            logger.info("size of the obj list " + list.size());

        } catch (ClassNotFoundException e) {
            logger.error(e.getException());
        }
        return list;
    }
}
