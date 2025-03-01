//package ir.maktabsharif.demofinalproject2.controller;
//
//import ir.maktabsharif.demofinalproject2.exeption.EntityNotFoundException;
//import ir.maktabsharif.demofinalproject2.model.Course;
//import ir.maktabsharif.demofinalproject2.model.Student;
//import ir.maktabsharif.demofinalproject2.model.Teacher;
//import ir.maktabsharif.demofinalproject2.model.UserAccount;
//import ir.maktabsharif.demofinalproject2.service.CourseService;
//import ir.maktabsharif.demofinalproject2.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/courses")
//public class CourseController {
//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private UserService userService;
//
//
//    @PostMapping("/create")
//    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
//        Course saved = courseService.saveOrUpdate(course);
//        return new ResponseEntity<>(saved, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/assignTeacherToCourse/{courseId}/{teacherId}")
//    public ResponseEntity<String> assignTeacherToCourse(@PathVariable Integer courseId, @PathVariable Long teacherId) {
//        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
//        Optional<UserAccount> optionalUser = userService.getUserById(teacherId);
//        if (optionalCourse.isEmpty()) {
//            throw new EntityNotFoundException("This course does not exist");
//        }
//        if (optionalUser.isEmpty() || !(optionalUser.get() instanceof Teacher)) {
//            throw new EntityNotFoundException("Teacher with id " + teacherId + " does not exist or is not a teacher");
//        }
//        courseService.addTeacherToCourse(optionalCourse.get(), (Teacher) optionalUser.get());
//        return ResponseEntity.status(HttpStatus.OK).body("Successfully assigned teacher to course " + courseId);
//    }
//
//
//    @PutMapping("/assignStudentsToCourse/{courseId}")
//    public ResponseEntity<String> assignStudentsToCourse(@PathVariable Integer courseId, @RequestBody List<Student> students) {
//
//        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
//        if (optionalCourse.isEmpty()) {
//            throw new EntityNotFoundException("This course does not exist");
//        }
//        courseService.addStudentsToCourse(optionalCourse.get(), students);
//        return ResponseEntity.status(HttpStatus.OK).body("Students Successfully assigned to course " + courseId);
//    }
//
//    @PutMapping("/update/{courseId}")
//    public ResponseEntity<Course> updateCourse(@PathVariable Integer courseId, @RequestBody Course course) {
//        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
//        if (optionalCourse.isEmpty()) {
//            throw new EntityNotFoundException("This course does not exist");
//        }
//        course.setId(courseId);
//        Course updatedCourse = courseService.saveOrUpdate(course);
//        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
//
//    }
//
//    @DeleteMapping("/delete/{courseId}")
//    public ResponseEntity<String> deleteCourse(@PathVariable Integer courseId) {
//        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
//        if (optionalCourse.isEmpty()) {
//            throw new EntityNotFoundException("This course does not exist for delete");
//        }
//        courseService.deleteCourseById(courseId);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course successfully deleted");
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Course>> getAllCourses() {
//        List<Course> allCourses = courseService.getAllCourses();
//        return ResponseEntity.ok(allCourses);
//    }
//
//    @GetMapping("/person/{courseId}")
//    public ResponseEntity<Map<String, Object>> getCoursePeople(@PathVariable Integer courseId) {
//        Map<String, Object> response = courseService.getCoursePeople(courseId);
//
//        if (response.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(response);
//    }
//
//
//    @GetMapping("/teacher/{teacherId}")
//    public ResponseEntity<List<Course>> getTeacherCourses(@PathVariable Long teacherId) {
//        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
//        return ResponseEntity.ok(courses);
//    }
//
//}
