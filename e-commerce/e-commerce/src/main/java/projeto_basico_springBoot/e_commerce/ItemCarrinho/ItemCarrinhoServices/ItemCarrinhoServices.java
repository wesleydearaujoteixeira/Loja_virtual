package projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinhoServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Cliente.ClienteRepository;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinho;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinhoRepository;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoRepository;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.UsuarioRepositoy;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCarrinhoServices {

    private static final Logger logger = LoggerFactory.getLogger(ItemCarrinhoServices.class);

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    @Autowired
    private ClienteRepository clienteRepository;

    public ItemCarrinho adicionarProdutoAoCarrinho(Long produtoId, int quantidade, Long usuarioId) {
        Cliente cliente = buscarClientePorUsuarioId(usuarioId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Você não pode comprar o seu próprio produto.");
        }

        ItemCarrinho novoItem = new ItemCarrinho();
        novoItem.setProduto(produto);
        novoItem.setQuantidade(quantidade);
        novoItem.setCliente(cliente);

        logger.info("Adicionando produto {} ao carrinho do cliente {}", produtoId, cliente.getId());

        return itemCarrinhoRepository.save(novoItem);
    }

    public List<ItemCarrinho> listCarrinho(Long usuarioId) {
        Cliente cliente = buscarClientePorUsuarioId(usuarioId);
        List<ItemCarrinho> itens = itemCarrinhoRepository.findByCliente(cliente);
        logger.info("Listando {} itens do carrinho para cliente {}", itens.size(), cliente.getId());
        return itens;
    }

    public void deleteItemByCart(Long usuarioId, Long produtoId) {
        Cliente cliente = buscarClientePorUsuarioId(usuarioId);
        List<ItemCarrinho> itens = itemCarrinhoRepository.findByCliente(cliente);

        logger.info("Cliente {} tem {} itens no carrinho", cliente.getId(), itens.size());

        ItemCarrinho itemParaRemover = itens.stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

        itemCarrinhoRepository.delete(itemParaRemover);

        logger.info("Produto {} removido do carrinho do cliente {}", produtoId, cliente.getId());
    }

    public void zerarCarrinho(Long usuarioId) {
        Cliente cliente = buscarClientePorUsuarioId(usuarioId);
        List<ItemCarrinho> itens = itemCarrinhoRepository.findByCliente(cliente);

        logger.info("Zerando carrinho do cliente {}, removendo {} itens", cliente.getId(), itens.size());

        itemCarrinhoRepository.deleteAll(itens);
    }

    // Método auxiliar para centralizar a busca do cliente
    private Cliente buscarClientePorUsuarioId(Long usuarioId) {
        Optional<Cliente> clienteOpt = clienteRepository.findClientByUsuario_Id(usuarioId);
        if (clienteOpt.isEmpty()) {
            logger.error("Cliente não encontrado para usuário ID {}", usuarioId);
            throw new RuntimeException("Cliente não encontrado");
        }
        return clienteOpt.get();
    }
}
