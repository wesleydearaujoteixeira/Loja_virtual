package projeto_basico_springBoot.e_commerce.User.userService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.UsuarioRepositoy;

@Service
public class UserService {

    @Autowired
    UsuarioRepositoy usuarioRepositoy;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Users cadastrar(Users user) {
        if (user == null || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        // Codifica a senha recebida no objeto user
        user.setSenha(passwordEncoder.encode(user.getPassword()));

        // Salva e retorna o usuário com a senha criptografada
        return usuarioRepositoy.save(user);
    }

}
