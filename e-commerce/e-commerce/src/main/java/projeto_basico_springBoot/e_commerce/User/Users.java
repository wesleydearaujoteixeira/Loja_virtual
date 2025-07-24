package projeto_basico_springBoot.e_commerce.User;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Produto.Produto;

import java.util.Collection;
import java.util.List;

@Entity
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** e-mail será usado como “username” no login  */
    @Column(nullable = false, unique = true)
    private String email;

    /** senha já criptografada; nunca deve ser exposta em JSON */
    @Column(nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "usuario")
    private Cliente cliente;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "usuario-produto")
    private List<Produto> produtos;

    /* ========== MÉTODOS UserDetails ========== */

    /** Caso futuramente você tenha perfis, substitua List.of() por roles reais */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {        // Spring Security usa este campo
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override  public boolean isAccountNonExpired()     { return true; }
    @Override  public boolean isAccountNonLocked()      { return true; }
    @Override  public boolean isCredentialsNonExpired() { return true; }
    @Override  public boolean isEnabled()               { return true; }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void atualizarSenhaCriptografada(String senhaCriptografada) {
        this.senha = senhaCriptografada;
    }
}
