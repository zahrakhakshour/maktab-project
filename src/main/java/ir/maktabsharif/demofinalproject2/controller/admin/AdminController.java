package ir.maktabsharif.demofinalproject2.controller.admin;

import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
import ir.maktabsharif.demofinalproject2.exeption.UserTypeMismatchException;
import ir.maktabsharif.demofinalproject2.model.Course;
import ir.maktabsharif.demofinalproject2.model.Student;
import ir.maktabsharif.demofinalproject2.model.Teacher;
import ir.maktabsharif.demofinalproject2.model.UserAccount;
import ir.maktabsharif.demofinalproject2.model.dto.UpdatableUserRequest;
import ir.maktabsharif.demofinalproject2.model.dto.UserResponse;
import ir.maktabsharif.demofinalproject2.service.CourseService;
import ir.maktabsharif.demofinalproject2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    public AdminController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }



    @PreAuthorize("hasAuthority('EDIT_USER')")
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdatableUserRequest updatableUserRequest) {

        UserResponse updated = userService.update(updatableUserRequest);
        if (updated != null) {return ResponseEntity.status(HttpStatus.OK).body(updated);}

        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @PreAuthorize("hasAuthority('APPROVE_USER')")
    @PutMapping("/confirm/{userId}")
    public ResponseEntity<String> confirm(@PathVariable Long userId) {
        if(userService.confirmUser(userId)){
            return ResponseEntity.status(HttpStatus.OK).body("Confirmation for user "+userId+" successfully");
        }
        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @PreAuthorize("hasAuthority('SEARCH_USER')")
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> search(
            @RequestParam String role ,
            @RequestParam String letter) {
        List<UserResponse> responseList = userService.filterByRoleAndLetters(role, letter);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasAuthority('SEARCH_USER')")
    @GetMapping("/searchByRole")
    public ResponseEntity<List<UserResponse>> searchByRole(
            @RequestParam String role
    ){
        List<UserResponse> responseList = userService.filterByRole(role);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasAuthority('SEARCH_USER')")
    @GetMapping("/searchByLetter")
    public ResponseEntity<List<UserResponse>> searchByLetter(@RequestParam String letter) {
        List<UserResponse> responseList = userService.filterByLetters(letter);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_USERS')")
    @GetMapping("/allPerson")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course saved = courseService.saveOrUpdate(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADD_TEACHER_TO_COURSE')")
    @PutMapping("/assignTeacherToCourse/{courseId}/{teacherId}")
    public ResponseEntity<String> assignTeacherToCourse(@PathVariable Integer courseId, @PathVariable Long teacherId) {
        try {

            courseService.addTeacherToCourse(courseId, teacherId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully assigned teacher to course " + courseId);
        } catch (EntityNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserTypeMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADD_STUDENTS_TO_COURSE')")
    @PutMapping("/assignStudentsToCourse/{courseId}")
    public ResponseEntity<String> assignStudentsToCourse(@PathVariable Integer courseId, @RequestBody List<Student> students) {

        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new EntityNotFoundException("This course does not exist");
        }
        courseService.addStudentsToCourse(optionalCourse.get(), students);
        return ResponseEntity.status(HttpStatus.OK).body("Students Successfully assigned to course " + courseId);
    }

    @PreAuthorize("hasAuthority('EDIT_COURSE')")
    @PutMapping("/update/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer courseId, @RequestBody Course course) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new EntityNotFoundException("This course does not exist");
        }
        course.setId(courseId);
        Course updatedCourse = courseService.saveOrUpdate(course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')")
    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new EntityNotFoundException("This course does not exist for delete");
        }
        courseService.deleteCourseById(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course successfully deleted");
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_COURSES')")
    @GetMapping("/allCourses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = courseService.getAllCourses();
        return ResponseEntity.ok(allCourses);
    }

    @PreAuthorize("hasAuthority('VIEW_PERSON_IN_COURSE')")
    @GetMapping("/person/{courseId}")
    public ResponseEntity<Map<String, Object>> getCoursePeople(@PathVariable Integer courseId) {
        Map<String, Object> response = courseService.getCoursePeople(courseId);

        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

}
