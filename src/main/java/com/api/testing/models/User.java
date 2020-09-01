package com.api.testing.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonDeserialize(builder = User.UserBuilder.class)
@Builder(builderClassName = "UserBuilder", toBuilder = true)
public class User {

    private String name;
    private String job;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {
    }
}
