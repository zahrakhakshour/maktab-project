package ir.maktabsharif.demofinalproject2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "userAccount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserAccount extends BaseEntity<Long> {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String nationalCode;
    @ManyToOne
    private Role role;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private UserStatus status;


}
