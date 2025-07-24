package projeto_basico_springBoot.e_commerce.Produto.ProdutoServices;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoRepository;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.UsuarioRepositoy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServices {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepositoy usuarioRepository;


    @Autowired
    Cloudinary cloudinary;

    public Produto CadastrarProduto(
            String nome,
                Double price,
            String descricao,
            MultipartFile imagem,
            Long usuarioId

    ) throws IOException {


        Users usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));


        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPrice(price);
        produto.setUsuario(usuario);

        if (imagem != null && !imagem.isEmpty()) {
            // Define o diretório onde a imagem será salva
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

            produto.setProdutoFoto(urlImagemCloudinary);

        }


       return produtoRepository.save(produto);

    }

    public Produto atualizarProduto(
            Long produtoId,
            String nome,
            Double price,
            String descricao,
            Long usuarioId,
            MultipartFile imagem
    ) throws IOException {

        // Busca o produto
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + produtoId + " não encontrado."));

        // Busca o usuário
        Users usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));

         if (!produto.getUsuario().getId().equals(usuarioId)) {
             throw new SecurityException("Você não tem permissão para editar este produto.");
         }

        // Atualiza os campos
        if (nome != null) produto.setNome(nome);
        if (descricao != null) produto.setDescricao(descricao);
        if (price != null) produto.setPrice(price);

        // Atualiza imagem, se enviada
        if (imagem != null && !imagem.isEmpty()) {
            File arquivoTemporario = Files.createTempFile("upload", imagem.getOriginalFilename()).toFile();
            imagem.transferTo(arquivoTemporario);

            Map resultado = cloudinary.uploader().upload(arquivoTemporario, ObjectUtils.emptyMap());

            String urlImagemCloudinary = (String) resultado.get("secure_url");

            produto.setProdutoFoto(urlImagemCloudinary);
        }

        return produtoRepository.save(produto);
    }


    public List<Produto> GetAllProdutos (String name) {
        return produtoRepository.findByNomeIgnoringSpaces(name);
    }

    public boolean DeletandoUmProduto (Long idProduto, Long idUsuario) {
        Optional<Produto> produtoDelete = produtoRepository.findById(idProduto);

        if(produtoDelete.isPresent()){

            Produto produto = produtoDelete.get();

            System.out.println("Usuário dono do produto: " + produto.getUsuario());


//           if(!produto.getUsuario().getId().equals(idUsuario)) {
//                throw new SecurityException("Você não tem permissão para deletar este produto.");
//
//            }

            produtoRepository.deleteById(idProduto);
            return true;

        }

        return false;


    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> Allproducts () {
        return produtoRepository.findAll();
    }


    public List<Produto> getProductsByUser (Long usuarioId) {
        Users usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("usuário com o id "+ usuarioId + " Não corresponde!"));

        return produtoRepository.findByUsuario(usuario);

    }

    // ProdutoServices.java
    public List<Produto> getProdutosExcetoDoUsuario(Long usuarioId) {
        return produtoRepository.findAllExcludingUser(usuarioId);
    }





}
