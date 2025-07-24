package projeto_basico_springBoot.e_commerce.ItemCarrinho;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import projeto_basico_springBoot.e_commerce.Carrinho.Carrinho;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Pedidos.Pedidos;
import projeto_basico_springBoot.e_commerce.Produto.Produto;

@Entity
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Produto produto;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "carrinho_id") // nome da coluna FK no banco
    private Carrinho carrinho;


    private int quantidade;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}