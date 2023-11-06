package models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseModel {
    @JsonAlias("type")
    boolean typeTrue;
    String text;
    String message;
    @JsonIgnore
    Map<String, String> userName;
    @JsonIgnore
    Map<String, String> login;
    @JsonIgnore
    Map<String, String> phoneNumber;
    @JsonIgnore
    Map<String, String> password;
    @JsonIgnore
    Map<String, String> passwordValidation;
}
