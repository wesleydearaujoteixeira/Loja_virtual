package projeto_basico_springBoot.e_commerce.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projeto_basico_springBoot.e_commerce.User.Users;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produto p " +
            "WHERE LOWER(REPLACE(p.nome, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:term, ' ', ''), '%'))",
            nativeQuery = true)
    List<Produto> findByNomeIgnoringSpaces(@Param("term") String term);

    List<Produto> findByUsuario(Users usuario);

    @Query("SELECT p FROM Produto p WHERE p.usuario.id <> :usuarioId")
    List<Produto> findAllExcludingUser(@Param("usuarioId") Long usuarioId);

}
