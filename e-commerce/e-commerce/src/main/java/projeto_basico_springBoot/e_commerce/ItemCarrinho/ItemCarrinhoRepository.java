package projeto_basico_springBoot.e_commerce.ItemCarrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;

import java.util.List;

public interface ItemCarrinhoRepository extends JpaRepository <ItemCarrinho, Long> {

    List<ItemCarrinho> findByCliente(Cliente cliente);
}


