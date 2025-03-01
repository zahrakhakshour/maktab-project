//package ir.maktabsharif.demofinalproject2.controller;
//
//import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
//import ir.maktabsharif.demofinalproject2.exeption.UsernameAlreadyTakenException;
//import ir.maktabsharif.demofinalproject2.model.Role;
//import ir.maktabsharif.demofinalproject2.model.UserAccount;
//import ir.maktabsharif.demofinalproject2.model.dto.UpdatableUserRequest;
//import ir.maktabsharif.demofinalproject2.model.dto.UserRequest;
//import ir.maktabsharif.demofinalproject2.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//
//    private final UserService userService;
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<UserAccount> signup(@RequestBody @Valid UserRequest userRequest) {
//        UserAccount savedUser = userService.save(userRequest);
//        if(savedUser != null) {
//            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<String> updateUser(@RequestBody UpdatableUserRequest updatableUserRequest) {
//
//        UserAccount updated = userService.update(updatableUserRequest);
//        if (updated != null) {return ResponseEntity.status(HttpStatus.OK).body("updated successfully!");}
//
//        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed!");}
//    }
//
//
//    @PutMapping("/confirm/{userId}")
//    public ResponseEntity<String> confirm(@PathVariable Long userId) {
//        Optional<UserAccount> optionalUser = userService.getUserById(userId);
//        if (optionalUser.isPresent()) {
//            userService.confirmUser(optionalUser.get());
//            return ResponseEntity.ok("User confirmed");
//        }
//        throw new EntityNotFoundException("User not found");
//    }
//
//
//
////    @GetMapping("/search")
////    public ResponseEntity<List<UserAccount>> search(
////            @RequestParam String role ,
////            @RequestParam String letter) {
////        List<UserAccount> userList = userService.filterByRoleAndLetters(role, letter);
////        return ResponseEntity.ok(userList);
////    }
////
////    @GetMapping("/searchByRole")
////    public ResponseEntity<List<UserAccount>> searchByRole(
////            @RequestParam String role
////    ){
////        List<UserAccount> userAccountList = userService.filterByRole(role);
////        return ResponseEntity.ok(userAccountList);
////    }
////
////    @GetMapping("/searchByLetter")
////    public ResponseEntity<List<UserAccount>> searchByLetter(@RequestParam String letter) {
////        List<UserAccount> userAccountList = userService.filterByLetters(letter);
////        return ResponseEntity.ok(userAccountList);
////    }
////
////
////    @GetMapping("/all")
////    public ResponseEntity<List<UserAccount>> getAllUsers() {
////        List<UserAccount> allUsers = userService.getAllUsers();
////        return ResponseEntity.ok(allUsers);
////    }
//
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
//        Optional<UserAccount> optionalUserAccount = userService.getUserById(id);
//        if(optionalUserAccount.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
//        }
//
//        userService.deleteUserById(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//
//}
