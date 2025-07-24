package projeto_basico_springBoot.e_commerce.Pedidos.PedidosDTO;


public class ItemCarrinhoDTO {
    private Long idProduto;
    private String nomeProduto;
    private int quantidade;
    private Double price;
    private String fotoProduto;

    public ItemCarrinhoDTO(Long idProduto, String nomeProduto, int quantidade, double price, String fotoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.fotoProduto = fotoProduto;
        this.price = price;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getFotoProduto() {
        return fotoProduto;
    }

    public void setFotoProduto(String fotoProduto) {
        this.fotoProduto = fotoProduto;
    }
}
