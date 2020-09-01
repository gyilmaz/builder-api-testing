package com.api.testing.requests;

import com.api.testing.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;


public class DeleteRequest extends BaseAPITest {


    public static ValidatableResponse deleteValidatableResponse(String location){
        logger.info("{}", location);
        RestAssured.baseURI = location;

        return RestAssured.given().when().
//                log().all().
                delete().
//                peek().       //show the Server's responding HTTP protocol version along with status response code plug response headers
                then();
    }

}
