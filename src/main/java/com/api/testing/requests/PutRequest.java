package com.api.testing.requests;

import com.api.testing.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class PutRequest extends BaseAPITest {

    public static ValidatableResponse putValidatableResponse(String location, Object obj){
        logger.info("{}", location);
        RestAssured.baseURI = location;
        return RestAssured.given().
//                log().all().
                when().
                body(obj).
                put().
                then();
    }


}
