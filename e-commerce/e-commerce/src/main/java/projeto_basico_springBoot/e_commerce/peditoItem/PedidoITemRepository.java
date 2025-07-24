package projeto_basico_springBoot.e_commerce.peditoItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoITemRepository extends JpaRepository<PedidoItem, Long> {
}
