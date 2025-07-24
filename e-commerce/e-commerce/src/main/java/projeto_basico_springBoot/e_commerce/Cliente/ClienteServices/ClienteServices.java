package projeto_basico_springBoot.e_commerce.Cliente.ClienteServices;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Cliente.ClienteRepository;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.UsuarioRepositoy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteServices {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioRepositoy usuarioRepository;


    @Autowired
    Cloudinary cloudinary;



    public Cliente criarClienteParaUsuario(Long usuarioId, String nome,  String endereco, String cpf, MultipartFile imagem) throws IOException {
        // Verifica se o usuário existe

        Users usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));

        // Cria o cliente e associa ao usuário
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setCpf(cpf);
        cliente.setUsuario(usuario);

        if (imagem != null && !imagem.isEmpty()) {

            // Salva a imagem
            // Salve a URL acessível publicamente

            String pastaUpload = "uploads/";
            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path caminhoCompleto = Paths.get(pastaUpload, nomeArquivo);

            Files.createDirectories(caminhoCompleto.getParent());
            Files.write(caminhoCompleto, imagem.getBytes());

            // Upload para Cloudinary
            File arquivoTemporario = Files.createTempFile("upload", imagem.getOriginalFilename()).toFile();
            imagem.transferTo(arquivoTemporario);

            Map resultado = cloudinary.uploader().upload(arquivoTemporario, ObjectUtils.emptyMap());

            String urlImagemCloudinary = (String) resultado.get("secure_url");

            cliente.setFotoUrl(urlImagemCloudinary);


        }

        clienteRepository.save(cliente);
        return cliente;
    }


    public Optional<Cliente> atualizarCliente(Long usuarioId, String nome, String endereco, String cpf, MultipartFile imagem) {
        Optional<Cliente> clienteExisting = clienteRepository.findClientByUsuario_Id(usuarioId);

        if (clienteExisting.isPresent()) {
            Cliente cliente = clienteExisting.get();

            // Atualiza somente campos que foram passados (não nulos e não vazios)
            if (nome != null && !nome.trim().isEmpty()) {
                cliente.setNome(nome);
            }

            if (endereco != null && !endereco.trim().isEmpty()) {
                cliente.setEndereco(endereco);
            }

            if (cpf != null && !cpf.trim().isEmpty()) {
                cliente.setCpf(cpf);
            }

            // Atualiza a imagem (caso tenha sido enviada)
            if (imagem != null && !imagem.isEmpty()) {
                try {
                    File arquivoTemporario = Files.createTempFile("upload", imagem.getOriginalFilename()).toFile();
                    imagem.transferTo(arquivoTemporario);

                    Map resultado = cloudinary.uploader().upload(arquivoTemporario, ObjectUtils.emptyMap());
                    String urlImagemCloudinary = (String) resultado.get("secure_url");

                    cliente.setFotoUrl(urlImagemCloudinary);

                    arquivoTemporario.delete();

                } catch (IOException e) {
                    throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage());
                }
            }

            // Salva e retorna
            return Optional.of(clienteRepository.save(cliente));
        }

        return Optional.empty();
    }

    public Optional<Cliente> findOneClient(Long usuarioId) {

        return clienteRepository.findClientByUsuario_Id(usuarioId);


    }


}


