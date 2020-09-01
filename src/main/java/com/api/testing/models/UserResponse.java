package com.api.testing.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Data
@Getter
@Setter
@JsonDeserialize(builder = UserResponse.UserResponseBuilder.class)
@Builder(builderClassName = "UserResponseBuilder", toBuilder = true)
public class UserResponse {

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPOJOBuilder(withPrefix = "")
    public static class UserResponseBuilder {
    }
}
