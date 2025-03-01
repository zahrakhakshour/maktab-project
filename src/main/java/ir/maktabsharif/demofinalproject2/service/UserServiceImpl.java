package ir.maktabsharif.demofinalproject2.service;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.exeption.UsernameAlreadyTakenException;
import ir.maktabsharif.demofinalproject2.model.*;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableUserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserResponse;
import ir.maktabsharif.demofinalproject2.repository.RoleRepository;
import ir.maktabsharif.demofinalproject2.repository.StudentRepository;
import ir.maktabsharif.demofinalproject2.repository.TeacherRepository;
import ir.maktabsharif.demofinalproject2.repository.UserRepository;
import ir.maktabsharif.demofinalproject2.security.BCryptPasswordEncode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse save(UserRequest userRequest) {
       Role role = roleRepository.findById(userRequest.roleId())
               .orElseThrow(() -> new EntityNotFoundException("Invalid role"));


        String encodeBcryptPassword = BCryptPasswordEncode.encodeBcryptPassword(userRequest.password());


        if (checkUnique(userRequest.userName(), null)) {


            if (role.getName().equals("ROLE_STUDENT")) {
                Student student = Student.builder()
                        .firstName(userRequest.firstName())
                        .lastName(userRequest.lastName())
                        .email(userRequest.email())
                        .phone(userRequest.phone())
                        .password(encodeBcryptPassword)
                        .birthDate(userRequest.birthDate())
                        .nationalCode(userRequest.nationalCode())
                        .role(role)
                        .status(UserStatus.PendingConfirmation)
                        .userName(userRequest.userName())
                        .build();
                Student saved = studentRepository.save(student);
                return new UserResponse(
                        saved.getId(),
                        saved.getFirstName(),
                        saved.getLastName(),
                        saved.getEmail(),
                        saved.getPhone(),
                        saved.getUserName(),
                        saved.getNationalCode(),
                        saved.getRole(),
                        saved.getBirthDate(),
                        saved.getStatus()
                );

            }

            if (role.getName().equals("ROLE_TEACHER")) {
                Teacher teacher = Teacher.builder()
                        .firstName(userRequest.firstName())
                        .lastName(userRequest.lastName())
                        .email(userRequest.email())
                        .phone(userRequest.phone())
                        .password(encodeBcryptPassword)
                        .birthDate(userRequest.birthDate())
                        .nationalCode(userRequest.nationalCode())
                        .role(role)
                        .status(UserStatus.PendingConfirmation)
                        .userName(userRequest.userName())
                        .build();
                Teacher saved = teacherRepository.save(teacher);
                return new UserResponse(
                        saved.getId(),
                        saved.getFirstName(),
                        saved.getLastName(),
                        saved.getEmail(),
                        saved.getPhone(),
                        saved.getUserName(),
                        saved.getNationalCode(),
                        saved.getRole(),
                        saved.getBirthDate(),
                        saved.getStatus()
                );
            }
        }
        throw new UsernameAlreadyTakenException("Username is already taken");

    }


    @Override
    public UserResponse update(UpdatableUserRequest updatableUserRequest) {


        if (checkUnique(updatableUserRequest.userName(), updatableUserRequest.id())) {
            UserAccount userAccount = userRepository.findById(updatableUserRequest.id()).orElseThrow(() -> new EntityNotFoundException("You want to update a user that does not exist"));

            String firstName = updatableUserRequest.firstName() != null ? updatableUserRequest.firstName() : userAccount.getFirstName();
            String lastName = updatableUserRequest.lastName() != null ? updatableUserRequest.lastName() : userAccount.getLastName();
            String email = updatableUserRequest.email() != null ? updatableUserRequest.email() : userAccount.getEmail();
            String phone = updatableUserRequest.phone() != null ? updatableUserRequest.phone() : userAccount.getPhone();
            String nationalCode = updatableUserRequest.nationalCode() != null ? updatableUserRequest.nationalCode() : userAccount.getNationalCode();
            Date birthDate = updatableUserRequest.birthDate() != null ? updatableUserRequest.birthDate() : userAccount.getBirthDate();
            String userName = updatableUserRequest.userName() != null ? updatableUserRequest.userName() : userAccount.getUserName();
            UserStatus status = userAccount.getStatus() != null ? updatableUserRequest.status() : userAccount.getStatus();
            Role role = updatableUserRequest.roleId() != null ?
                    roleRepository.findById(updatableUserRequest.roleId())
                            .orElseThrow(() -> new EntityNotFoundException("Role not found"))
                    : userAccount.getRole();
            String password = updatableUserRequest.password() != null ? BCryptPasswordEncode.encodeBcryptPassword(updatableUserRequest.password()) : userAccount.getPassword();


            if (role.getName().equals("ROLE_STUDENT")) {
                Student student = Student.builder()
                        .id(updatableUserRequest.id())
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(phone)
                        .password(password)
                        .birthDate(birthDate)
                        .nationalCode(nationalCode)
                        .role(role)
                        .status(status)
                        .userName(userName)
                        .build();
                Student saved = studentRepository.save(student);
                return new UserResponse(
                        saved.getId(),
                        saved.getFirstName(),
                        saved.getLastName(),
                        saved.getEmail(),
                        saved.getPhone(),
                        saved.getUserName(),
                        saved.getNationalCode(),
                        saved.getRole(),
                        saved.getBirthDate(),
                        saved.getStatus()
                );
            }

            if (role.getName().equals("ROLE_TEACHER")) {
                Teacher teacher = Teacher.builder()
                        .id(updatableUserRequest.id())
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(phone)
                        .password(password)
                        .birthDate(birthDate)
                        .nationalCode(nationalCode)
                        .role(role)
                        .status(status)
                        .userName(userName)
                        .build();
                Teacher saved = teacherRepository.save(teacher);
                return new UserResponse(
                        saved.getId(),
                        saved.getFirstName(),
                        saved.getLastName(),
                        saved.getEmail(),
                        saved.getPhone(),
                        saved.getUserName(),
                        saved.getNationalCode(),
                        saved.getRole(),
                        saved.getBirthDate(),
                        saved.getStatus()
                );
            }
        }
        throw new UsernameAlreadyTakenException("Username is already taken");


    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        Optional<UserAccount> optionalUserAccount = userRepository.findById(id);
        if (optionalUserAccount.isPresent()) {
            UserAccount userAccount = optionalUserAccount.get();
            return Optional.of(new UserResponse(userAccount.getId(),
                    userAccount.getFirstName(),
                    userAccount.getLastName(),
                    userAccount.getEmail(),
                    userAccount.getPhone(),
                    userAccount.getUserName(),
                    userAccount.getNationalCode(),
                    userAccount.getRole(),
                    userAccount.getBirthDate(),
                    userAccount.getStatus()));
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public List<UserResponse> getAllUsers() {
        List<UserAccount> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getUserName(),
                user.getNationalCode(),
                user.getRole(),
                user.getBirthDate(),
                user.getStatus()
                )) .collect(Collectors.toList());
    }


    @Override
    public List<UserResponse> filterByLetters(String letters) {
        List<UserAccount> users = userRepository.findByLetters(letters);
        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getUserName(),
                        user.getNationalCode(),
                        user.getRole(),
                        user.getBirthDate(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> filterByRole(String role) {
        Role foundedRole = roleRepository.findByName(role)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role"));
        List<UserAccount> users = userRepository.findByRole(foundedRole.getId());
        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getUserName(),
                        user.getNationalCode(),
                        user.getRole(),
                        user.getBirthDate(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> filterByRoleAndLetters(String roleName, String letters) {
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role"));
        List<UserAccount> users = userRepository.filterByRoleAndLetters(role.getId(), letters);
        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getUserName(),
                        user.getNationalCode(),
                        user.getRole(),
                        user.getBirthDate(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean confirmUser(Long userId) {
        Optional<UserAccount> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserAccount user = optionalUser.get();
            user.setStatus(UserStatus.Confirmed);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    private boolean checkUnique(String userName, Long userId) {
        if (userId != null) {
            Optional<UserAccount> optionalUser = userRepository.findByUserName(userName);

            if (optionalUser.isPresent() && optionalUser.get().getId().equals(userId)) {
                return true;
            }
        } else {
            Optional<UserAccount> optionalUser = userRepository.findByUserName(userName);
            return optionalUser.isEmpty();
        }
        return true;
    }

    @Override
    public Teacher getCurrentTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            String currentTeacherUsername = authentication.getName();

            return teacherRepository.findByUserName(currentTeacherUsername)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        }
        throw new SecurityException("Unauthorized");
    }


}