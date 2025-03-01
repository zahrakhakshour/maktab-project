package ir.maktabsharif.demofinalproject2.model.dto;

import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.UserStatus;

import java.util.Date;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String userName,
        String nationalCode,
        Role role,
        Date birthDate,
        UserStatus status

) {
}
