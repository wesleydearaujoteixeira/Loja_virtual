package projeto_basico_springBoot.e_commerce.Pedidos;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    List<Pedidos> findByCliente(Cliente cliente);

}