package projeto_basico_springBoot.e_commerce.Categoria.CategoriaServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto_basico_springBoot.e_commerce.Categoria.Categoria;
import projeto_basico_springBoot.e_commerce.Categoria.CategoriaRepository;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServices {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Categoria criarCategoriaEAssociarProduto(Categoria categoria, Long idProduto) {
        // 1. Criar a nova categoria

        // 2. Buscar o produto existente pelo ID
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // 3. Associar o produto à nova categoria
        produto.setCategoria(categoria);

        // 4. Adicionar o produto à lista de produtos da categoria
        categoria.setProdutos(List.of(produto));

        // 5. Salvar a categoria (e o produto será salvo por cascade)
       return  categoriaRepository.save(categoria);
    }

    public List<Categoria> categoriaList () {
        return categoriaRepository.findAll();
    }

    public List<Categoria> FiltragemDeUmaCategoria (String nome) {

            String busca = nome.trim();
             String buscaP1 =  busca.substring(0, 1).toUpperCase() + busca.substring(1);

            return categoriaRepository.buscarPorNomeParcial(buscaP1);
    }


    public boolean deleteCategory (Long id) {

        Optional<Categoria> categoriaP2 = categoriaRepository.findById(id);

        if(categoriaP2.isPresent()){
            categoriaRepository.deleteById(id);
            return true;
        }

        return false;

    }

}
