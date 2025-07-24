package projeto_basico_springBoot.e_commerce.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositoy extends JpaRepository <Users, Long> {
    Optional<Users> findByEmail(String email);
}
