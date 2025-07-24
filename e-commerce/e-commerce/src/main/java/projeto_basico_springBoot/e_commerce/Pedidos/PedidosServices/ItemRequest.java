package projeto_basico_springBoot.e_commerce.Pedidos.PedidosServices;


public class ItemRequest {
    private Long produtoId;
    private int quantidade;

    // Getters e Setters

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}