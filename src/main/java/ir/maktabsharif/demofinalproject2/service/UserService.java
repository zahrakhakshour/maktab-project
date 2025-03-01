package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.UserAccount;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableUserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse save(UserRequest userRequest);
    UserResponse update(UpdatableUserRequest updatableUserRequest);
    Optional<UserResponse> getUserById(Long id);
    void deleteUserById(Long id);
    List<UserResponse> getAllUsers();
    boolean confirmUser(Long userId);
    List<UserResponse> filterByRoleAndLetters ( String role,String letters);
    List<UserResponse> filterByRole ( String role);
    List<UserResponse> filterByLetters ( String letters);
    Teacher getCurrentTeacher();


}
