package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UpdatableUserRequest(

        Long id,
        @NotBlank(message = "You must enter your First name")
        String firstName,
        @NotBlank(message = "You must enter your Last name")
        String lastName,
        @Email(message = "Please enter your email with this pattern: zahra@gmail.com")
        String email,
        @Size(min = 6, max = 16, message = "Password must have 6-16 characters")
        String password,
        @Size(min = 6, max = 16, message = "Your username must have 6-16 characters")
        String userName,
        @Pattern(regexp = "^(\\+98|0)?9\\d{9}$", message = "Please enter your phone number like this: +989151234567")
        String phone,
        @NotBlank(message = "You must enter your NationalCode")
        String nationalCode,
        Integer roleId,
        Date birthDate,
        UserStatus status
) {}
