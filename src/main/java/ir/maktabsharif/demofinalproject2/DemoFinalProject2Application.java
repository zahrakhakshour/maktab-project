package ir.maktabsharif.demofinalproject2;

import ir.maktabsharif.demofinalproject2.model.*;
import ir.maktabsharif.demofinalproject2.repository.CourseRepository;
import ir.maktabsharif.demofinalproject2.repository.RoleRepository;
import ir.maktabsharif.demofinalproject2.repository.StudentRepository;
import ir.maktabsharif.demofinalproject2.repository.UserRepository;
import ir.maktabsharif.demofinalproject2.service.CourseService;
import ir.maktabsharif.demofinalproject2.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoFinalProject2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoFinalProject2Application.class, args);
//        UserService userService = context.getBean(UserService.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
//        CourseService courseService = context.getBean(CourseService.class);
//        CourseRepository courseRepository = context.getBean(CourseRepository.class);
        StudentRepository studentRepository = context.getBean(StudentRepository.class);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
//        UserAccount user = UserAccount.builder()
//                .id(1L)
//                .userName("zahra2")
//                .password("54321").build();

//        Student student = Student.builder()
//                .userName("ocean2")
//                .password("1234")
//                .id(52L)
//                .firstName("darya2")
//                .build();
//userService.saveOrUpdate(student);

//        Optional<UserAccount> optional = userRepository.findByUserName("zahraaa");
//
//        System.out.println(optional.get().getFirstName());


//        Course course = Course.builder()
//                .name("java")
//                .capacity(30)
//                .build();
//        courseService.saveOrUpdate(course);

//        Optional<Course> java = courseRepository.findByName("java");
//        System.out.println("capacity of java:" + java.get().getCapacity());
//
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        roleRepository.save(role);
//        Role role2 = new Role();
//        role2.setName("ROLE_STUDENT");
//        roleRepository.save(role2);
//        Role role3 = new Role();
//        role3.setName("ROLE_TEACHER");
//        roleRepository.save(role3);


//
//        BCryptPasswordEncoder bCryptPasswordEncoder = context.getBean(BCryptPasswordEncoder.class);
//        String encode = bCryptPasswordEncoder.encode("12345678");
//        Optional<Role> byId = roleRepository.findById(1);
//        UserAccount userAccount = UserAccount.builder()
//                .userName("adminadmin")
//                .password(encode)
//                .role(byId.get())
//                .build();
//        userRepository.save(userAccount);












    }

}
