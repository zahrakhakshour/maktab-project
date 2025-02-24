package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {

    @NotBlank(message = "You Must enter your First name \n ")
    private String firstName;
    @NotBlank(message = "You Must enter your Last name \n ")
    private String lastName;
    @Email(message = "Please enter your email with this pattern: zahra@gmail.com \n")
    private String email;
    @Size(min = 6, max = 16 , message = "Password must Have 6-16 characters \n")
    private String password;
    @Size(min = 6, max = 16, message = "Your Username must Have 6-16 characters \n")
    private String userName;
    @Pattern(regexp = "^(\\+98|0)?9\\d{9}$", message = "Please Enter your phone number like this: +989151234567 \n")
    private String phone;
    @NotBlank(message = "You Must enter your NationalCode \n ")
    private String nationalCode;
    private Role role;
    private Date birthDate;


}
