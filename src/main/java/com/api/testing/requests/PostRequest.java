package com.api.testing.requests;

import com.api.testing.BaseAPITest;
import com.api.testing.TestCase;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;

public class PostRequest extends BaseAPITest {

    public static ValidatableResponse postValidatableResponse(String location, Object obj) {
        RestAssured.baseURI = location;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.useRelaxedHTTPSValidation();
        return RestAssured.given().when().
                body(obj).
                post().
                peek().       //show the Server's responding HTTP protocol version along with status response code plug response headers
                then();
    }

    public static ValidatableResponse create(String URL, Object obj){
        ValidatableResponse validatableResponse = postValidatableResponse(URL,obj);
        Assert.assertTrue(validatableResponse.extract().statusCode() == 201, "Status code is  " + validatableResponse.extract().statusCode());
    return  validatableResponse;
    }




}
