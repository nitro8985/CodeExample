package models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseModel {
    @JsonAlias("type")
    private boolean typeTrue;
    private String text;
    private String message;
    @JsonIgnore
    private Map<String, String> userName;
    @JsonIgnore
    private Map<String, String> login;
    @JsonIgnore
    private Map<String, String> phoneNumber;
    @JsonIgnore
    private Map<String, String> password;
    @JsonIgnore
    private Map<String, String> passwordValidation;
}
