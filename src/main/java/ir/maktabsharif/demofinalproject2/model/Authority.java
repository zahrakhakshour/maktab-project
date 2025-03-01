package ir.maktabsharif.demofinalproject2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity<Long> {
    @Column(unique=true , nullable = false)
    private String name;
}
