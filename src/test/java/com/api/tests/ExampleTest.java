package com.api.tests;

import com.api.testing.BaseAPITest;
import com.api.testing.TestCase;
import com.api.testing.models.User;
import com.api.testing.models.UserResponse;
import com.api.testing.requests.GetRequest;
import com.api.testing.requests.PostRequest;
import org.testng.annotations.Test;

import java.util.List;

public class ExampleTest extends TestCase {


    @Test(enabled = true)
    public void createTest() throws Exception {
       User user = User.builder()
               .name("Joe")
               .job("SDET")
               .build();

    PostRequest.create(BaseAPITest.CREATE_USER, user);

//        Assert.assertEquals(userResponse.getName(),user.getName(),"Name is  : " +user.getName());
//        Assert.assertEquals(userResponse.getJob(), user.getJob(), "Job is :" + user.getJob());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String id = "1";
        UserResponse userResponse = (UserResponse) GetRequest.getObjectById(BaseAPITest.SINGLE_USER.replaceAll("@@@id@@@", id), UserResponse.class);
        System.out.println(userResponse.toString());
        System.out.println(userResponse.getId());
    }

    @Test
    public void getListTest(){
        List<UserResponse> userResponseList = GetRequest.getDefaultObjectList(BaseAPITest.LIST_USERS, UserResponse.class,"data");
    }
}
