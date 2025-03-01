//package ir.maktabsharif.demofinalproject2.service;
//
//import ir.maktabsharif.demofinalproject2.model.Authority;
//import ir.maktabsharif.demofinalproject2.model.Role;
//import ir.maktabsharif.demofinalproject2.repository.AuthorityRepository;
//import ir.maktabsharif.demofinalproject2.repository.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private AuthorityRepository authorityRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Optional<Role> roleTeacher = roleRepository.findByName("ROLE_TEACHER");
//        Optional<Role> roleStudent = roleRepository.findByName("ROLE_STUDENT");
//        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
//
//
//        List<String> adminAuthorities = Arrays.asList(
//                "VIEW_ALL_USERS", "APPROVE_USER", "EDIT_USER", "SEARCH_USER",
//                "VIEW_ALL_COURSES", "CREATE_COURSE", "ADD_TEACHER_TO_COURSE",
//                "ADD_STUDENTS_TO_COURSE", "EDIT_COURSE" , "DELETE_COURSE" , "VIEW_PERSON_IN_COURSE"
//        );
//
//        if (roleAdmin.isPresent()) {
//            for (String authorityName : adminAuthorities) {
//                Authority authority = new Authority();
//                authority.setName(authorityName);
//                authorityRepository.save(authority);
//                roleAdmin.get().getAuthorities().add(authority);
//            }
//            roleRepository.save(roleAdmin.get());
//        }
//
//
//
//        List<String> teacherAuthorities = Arrays.asList(
//                "VIEW_TEACHER_COURSES",
//                "VIEW_EXAMS_IN_COURSE",
//                "EDIT_EXAM",
//                "DELETE_EXAM" ,
//                "CREATE_EXAM"
//        );
//        if (roleTeacher.isPresent()) {
//            for (String authorityName : teacherAuthorities) {
//                Authority authority = new Authority();
//                authority.setName(authorityName);
//                authorityRepository.save(authority);
//                roleTeacher.get().getAuthorities().add(authority);
//            }
//            roleRepository.save(roleTeacher.get());
//        }
//
//
//
//    }
//}
