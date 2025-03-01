package ir.maktabsharif.demofinalproject2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity<Integer>{
    @Column(unique=true,nullable=false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_authorities"
            , joinColumns = @JoinColumn(name = "role_id") ,
            inverseJoinColumns = @JoinColumn (name = "authority_id")
    )
    private Set<Authority> authorities;



}
