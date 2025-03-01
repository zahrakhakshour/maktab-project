package ir.maktabsharif.demofinalproject2.repository;

import ir.maktabsharif.demofinalproject2.model.Role;
import ir.maktabsharif.demofinalproject2.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String username);

    @Query("SELECT u FROM UserAccount u WHERE " +
            "u.role.id = :roleId AND " +
            "(u.firstName LIKE CONCAT('%', :letter, '%') " +
            "OR u.lastName LIKE CONCAT('%', :letter, '%') " +
            "OR u.userName LIKE CONCAT('%', :letter, '%'))")
    List<UserAccount> filterByRoleAndLetters(@Param("roleId") Integer roleId, @Param("letter") String letter);



        @Query("select u from UserAccount u where u.role.id = :roleId")
    List<UserAccount> findByRole(@Param("roleId") Integer roleId);



    @Query("SELECT u FROM UserAccount u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :letter, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :letter, '%')) " +
            "OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :letter, '%'))")
    List<UserAccount> findByLetters(@Param("letter") String letter);

}

