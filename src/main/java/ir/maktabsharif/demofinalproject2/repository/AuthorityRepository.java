package ir.maktabsharif.demofinalproject2.repository;

import ir.maktabsharif.demofinalproject2.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
