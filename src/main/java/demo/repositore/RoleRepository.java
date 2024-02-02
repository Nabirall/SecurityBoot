package demo.repositore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import demo.model.Role;


@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

}
