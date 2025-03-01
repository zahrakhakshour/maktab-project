package ir.maktabsharif.demofinalproject2.controller.shared;

import ir.maktabsharif.demofinalproject2.model.UserAccount;
import ir.maktabsharif.demofinalproject2.model.dto.UserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserResponse;
import ir.maktabsharif.demofinalproject2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("users")
public class SharedController {
    @Autowired
    public SharedController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest userRequest) {

        UserResponse responseDTO = userService.save(userRequest);

        if (responseDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Your Sign Up Failed"));
    }
}
