package projeto_basico_springBoot.e_commerce.Pedidos.PedidosDTO;

import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinho;

import java.util.List;

public class PedidosDTO {

    private Long idPedido;
    private String nome;
    private String cpf;
    private String fotoUrl;
    private String endereco;
    private List<ItemCarrinhoDTO> itensCarrinho;
    private Double valorTotal;

    public PedidosDTO(Long idPedido,
                      String nome,
                      String cpf,
                      String fotoUrl,
                      String endereco,
                      List<ItemCarrinhoDTO> itensCarrinho,
                      Double valor
                      ) {
        this.idPedido = idPedido;
        this.nome = nome;
        this.cpf = cpf;
        this.fotoUrl = fotoUrl;
        this.endereco = endereco;
        this.itensCarrinho = itensCarrinho;
        this.valorTotal = valor;
    }

    // Getters
    public Long getIdPedido() {
        return idPedido;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<ItemCarrinhoDTO> getItensCarrinho() {
        return itensCarrinho;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
