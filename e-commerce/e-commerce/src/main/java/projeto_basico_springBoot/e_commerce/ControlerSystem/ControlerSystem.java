package projeto_basico_springBoot.e_commerce.ControlerSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import projeto_basico_springBoot.e_commerce.Categoria.Categoria;
import projeto_basico_springBoot.e_commerce.Categoria.CategoriaServices.CategoriaServices;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinho;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinhoServices.ItemCarrinhoServices;
import projeto_basico_springBoot.e_commerce.Pedidos.Pedidos;

import projeto_basico_springBoot.e_commerce.Pedidos.PedidosServices.PedidoService;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoServices.ProdutoServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/system")
public class ControlerSystem {

    @Autowired
    CategoriaServices categoriaServices;


    @Autowired
    ItemCarrinhoServices itemCarrinhoServices;

    @Autowired
    ProdutoServices produtoServices;

    @PostMapping("/category/{idProduto}")
    public ResponseEntity<?>  System_out (@RequestBody Categoria categoria, @PathVariable Long idProduto) {

       Categoria categoriaP1 = categoriaServices.criarCategoriaEAssociarProduto(categoria, idProduto);
        return ResponseEntity.ok(categoriaP1);
    }



    @GetMapping("/categories")
    public ResponseEntity<?> CategoriaList () {
        List<Categoria> categoria = categoriaServices.categoriaList();
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }


    @DeleteMapping("deleteCategory/{id}")

    public ResponseEntity<?> DeleteCategory (@PathVariable Long id) {
        Boolean category = categoriaServices.deleteCategory(id);

        if(category){
           return ResponseEntity.status(HttpStatus.OK).body(" Categoria deletado com sucesso");
        }

        return ResponseEntity.badRequest().body(" Categoria não encontrada! ");

    }


    @DeleteMapping("/zerar/{clienteId}")
    public ResponseEntity<?> zerarCarrinho(@PathVariable Long clienteId) {
        itemCarrinhoServices.zerarCarrinho(clienteId);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @GetMapping("/carrinhoList/{clienteId}")
    public ResponseEntity<List<ItemCarrinho>> listarCarrinho(@PathVariable Long clienteId) {
        List<ItemCarrinho> itens = itemCarrinhoServices.listCarrinho(clienteId);
        return ResponseEntity.ok(itens);
    }

    @DeleteMapping("/{clienteId}/remover-item/{productId}")
    public ResponseEntity<Void> removerItemDoCarrinho(
            @PathVariable Long clienteId,
            @PathVariable Long productId) {

        itemCarrinhoServices.deleteItemByCart(clienteId, productId);
        return ResponseEntity.noContent().build(); // HTTP 204
    }


    @DeleteMapping("/deletandoProduto/{idProduto}/{idUser}")

    public ResponseEntity<?> deletandoProduto (@PathVariable Long idProduto, @PathVariable Long idUser){

        boolean deletandoProduto = produtoServices.DeletandoUmProduto(idProduto, idUser);

        if(deletandoProduto){
            return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso " + idProduto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não pode ser deletado! ");

    }

    @GetMapping("/category/busca/{nome}")
    public ResponseEntity<?> filtragemCategory (@PathVariable String nome) {
        List<Categoria> categoria = categoriaServices.FiltragemDeUmaCategoria(nome);

        if(!categoria.isEmpty()){
            return ResponseEntity.ok(categoria);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada! ");

    }



    @GetMapping("/meus_produtos/{idUser}")

    public ResponseEntity<?> getProdutosUser (@PathVariable Long idUser) {
        List<Produto> produtos = produtoServices.getProductsByUser(idUser);

        if(!produtos.isEmpty()){
            return ResponseEntity.ok(produtos);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não contém nenhum produto com esse usuário " + idUser);


    }









}
