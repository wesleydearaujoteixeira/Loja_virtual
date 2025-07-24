package projeto_basico_springBoot.e_commerce.Cliente.ClienteDTO;

import projeto_basico_springBoot.e_commerce.Cliente.Cliente;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String endereco;
    private String fotoUrl;

    public ClienteDTO(Long id, String nome, String cpf, String endereco, String fotoUrl) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.fotoUrl = fotoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
