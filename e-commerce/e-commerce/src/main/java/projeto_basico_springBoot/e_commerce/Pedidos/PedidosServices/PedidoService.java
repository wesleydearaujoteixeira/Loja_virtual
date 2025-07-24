package projeto_basico_springBoot.e_commerce.Pedidos.PedidosServices;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Cliente.ClienteRepository;
import projeto_basico_springBoot.e_commerce.Pedidos.Pedidos;
import projeto_basico_springBoot.e_commerce.Pedidos.PedidosRepository;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.UsuarioRepositoy;
import projeto_basico_springBoot.e_commerce.peditoItem.PedidoItem;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidosRepository pedidoRepository;

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Pedidos criarPedido(Long usuarioId, List<ItemRequest> itensRequest) throws Exception {


        Optional<Cliente> cliente = clienteRepository.findClientByUsuario_Id(usuarioId);

        Pedidos pedido = new Pedidos();
        pedido.setCliente(cliente.get());
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus("AGUARDANDO PAGAMENTO");

        Double valorTotal = 0.0;

        for (ItemRequest itemReq : itensRequest) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new Exception("Produto ID " + itemReq.getProdutoId() + " não encontrado"));

            PedidoItem item = new PedidoItem();
            item.setProduto(produto);
            item.setQuantidade(itemReq.getQuantidade());
            item.setPrecoUnitario(produto.getPrice());
            item.setPedido(pedido);

            pedido.getItens().add(item);

            // Calcular subtotal e somar ao total
            valorTotal += item.getPrecoUnitario() * item.getQuantidade();
        }

        pedido.setValorTotal(valorTotal);

        return pedidoRepository.save(pedido);
    }

    public List<Pedidos> buscarPedidosDoCliente(Long usuarioId) {
        Cliente client = clienteRepository.findClientByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return pedidoRepository.findByCliente(client);
    }

    public void deletarPedido(Long pedidoId, Long usuarioId) throws Exception {


        Optional<Cliente> client = clienteRepository.findClientByUsuario_Id(usuarioId);

        Optional<Pedidos> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isEmpty()) {
            throw new Exception("Pedido não encontrado");
        }

        Pedidos pedido = pedidoOpt.get();
        if (!pedido.getCliente().getId().equals(client.get().getId())) {
            throw new Exception("Esse pedido não pertence ao cliente.");
        }

        pedidoRepository.delete(pedido);
    }


    @Transactional
    public Pedidos atualizarStatus(Long pedidoId, String novoStatus, Long usuarioId) throws Exception {
        Pedidos pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new Exception("Pedido não encontrado"));

        Long clienteIdDoPedido = pedido.getCliente().getId();
        Long clienteIdDoUsuario = clienteRepository.findClientByUsuario_Id(usuarioId)
                .orElseThrow(() -> new Exception("Cliente não encontrado")).getId();

        if (!clienteIdDoPedido.equals(clienteIdDoUsuario)) {
            throw new Exception("Acesso negado: Pedido não pertence ao usuário");
        }

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

}
