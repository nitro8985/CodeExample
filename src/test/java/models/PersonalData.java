package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonalData {
    private String userName;
    private String login;
    private String phoneNumber;
    private String password;
    private String passwordValidation;
}
