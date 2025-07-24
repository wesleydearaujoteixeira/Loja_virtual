package projeto_basico_springBoot.e_commerce.AuthControler;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto_basico_springBoot.e_commerce.Categoria.Categoria;
import projeto_basico_springBoot.e_commerce.Categoria.CategoriaServices.CategoriaServices;
import projeto_basico_springBoot.e_commerce.Cliente.Cliente;
import projeto_basico_springBoot.e_commerce.Cliente.ClienteDTO.ClienteDTO;
import projeto_basico_springBoot.e_commerce.Cliente.ClienteServices.ClienteServices;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinho;
import projeto_basico_springBoot.e_commerce.ItemCarrinho.ItemCarrinhoServices.ItemCarrinhoServices;
import projeto_basico_springBoot.e_commerce.Produto.Produto;
import projeto_basico_springBoot.e_commerce.Produto.ProdutoServices.ProdutoServices;
import projeto_basico_springBoot.e_commerce.Security.JwtService;
import projeto_basico_springBoot.e_commerce.User.Users;
import projeto_basico_springBoot.e_commerce.User.userDTO.LoginResponse;
import projeto_basico_springBoot.e_commerce.User.userDTO.UserDTO;
import projeto_basico_springBoot.e_commerce.User.userService.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthControler {

    @Autowired
    UserService userService;

    @Autowired
    ItemCarrinhoServices itemCarrinhoService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ClienteServices clienteServices;

    @Autowired
    private ProdutoServices produtoServices;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;

    @GetMapping("/pdt")
    public String getProduts () {
        return "Funcionando";
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<UserDTO> cadastro(@RequestBody Users user) {

            Users novoUsuario = userService.cadastrar(user);
            UserDTO usuarioDTO = new UserDTO(novoUsuario.getId(), novoUsuario.getEmail());
            return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {


        System.out.println(user);
        // Validação simples de entrada
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getSenha() == null || user.getSenha().isEmpty()) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios.");
        }



        try {
            // Tenta autenticar com as credenciais fornecidas
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha())
            );

            Users authenticatedUser = (Users) auth.getPrincipal();

            // Gera o token JWT se a autenticação for bem-sucedida
            String token = jwtService.generateToken((UserDetails) auth.getPrincipal());

            // Retorna o token JWT no corpo da resposta

            LoginResponse data = new LoginResponse(authenticatedUser.getId(), token, authenticatedUser.getUsername());


            return  ResponseEntity.ok(data);




            //return ResponseEntity.ok(data);
        } catch (AuthenticationException e) {
            // Exceção de autenticação - usuário ou senha inválidos
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
        } catch (Exception e) {
            // Captura de outras exceções
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }



    @PatchMapping("/atualizar_produto")
    public ResponseEntity<?> AtualizarProduto (
            @RequestParam Long produtoId,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam String price,
            @RequestParam Long usuarioId,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        try {
            double precoConvertido = Double.parseDouble(price.replace(",", ".")); // substitui vírgula por ponto, se necessário

            Produto pdt = produtoServices.atualizarProduto(produtoId, nome, precoConvertido, descricao, usuarioId, imagem);

            return ResponseEntity.ok(pdt);

        } catch (NumberFormatException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Preço inválido: não foi possível converter '" + price + "' para número.");
        }
    }


    @PostMapping("/create/cliente")
    public ResponseEntity<?> criarClienteParaUsuario(
            @RequestParam Long usuarioId,
            @RequestParam String nome,
            @RequestParam String endereco,
            @RequestParam String cpf,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        if (usuarioId == null || nome == null || endereco == null || cpf == null) {
            return ResponseEntity.badRequest().body("Campos obrigatórios estão faltando.");
        }

        Cliente clientes = clienteServices.criarClienteParaUsuario(usuarioId, nome, endereco, cpf, imagem);

        ClienteDTO clienteDto = new ClienteDTO(
                clientes.getId(),
                clientes.getNome(),
                clientes.getCpf(),
                clientes.getEndereco(),
                clientes.getFotoUrl()
        );

        return ResponseEntity.ok(clienteDto);
    }

//    @PatchMapping("/atualizar/cliente")
//    public ResponseEntity<?> updateCliente(
//            @RequestParam Long usuarioId,
//            @RequestParam(required = false) String nome,
//            @RequestParam(required = false) String endereco,
//            @RequestParam(required = false) String cpf,
//            @RequestParam(required = false) MultipartFile imagem
//    ) throws IOException {
//
//        Optional<Cliente> clienteOpt = clienteServices.atualizarCliente(usuarioId, nome, endereco, cpf, imagem);
//
//        if (clienteOpt.isPresent()) {
//            Cliente cliente = clienteOpt.get();
//
//
//            ClienteDTO dto = new ClienteDTO(
//                    cliente.getId(),
//                    cliente.getNome(),
//                    cliente.getCpf(),
//                    cliente.getEndereco(),
//                    cliente.getFotoUrl()
//            );
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.badRequest().body("Cliente não encontrado ou não autorizado.");
//    }


    @GetMapping("/encontrar/cliente/{id}")

    public ResponseEntity<?> encontrarClienteLogado (@PathVariable Long id) {
        Optional<Cliente> client = clienteServices.findOneClient(id);


       if(client.isPresent()){
           Cliente cliente = client.get();

           ClienteDTO dtoCliente = new ClienteDTO(
                   cliente.getId(),
                   cliente.getNome(),
                   cliente.getCpf(),
                   cliente.getEndereco(),
                   cliente.getFotoUrl()
           );


           return ResponseEntity.ok(dtoCliente);
       }

       return ResponseEntity.badRequest().build();

    }


    @GetMapping("/produto/{id}")
    public ResponseEntity<?> findOneProduto (@PathVariable Long id) {

        Optional<Produto> produto = produtoServices.buscarProdutoPorId(id);

        if(!produto.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado! ");

        }

        return ResponseEntity.ok(produto);


    }


    @GetMapping("/produtos")

    public ResponseEntity<?> produtosAll () {
         List<Produto> produtos = produtoServices.Allproducts();

         if(!produtos.isEmpty()){
             return ResponseEntity.ok(produtos);
         }

         return ResponseEntity.badRequest().build();
    }

    @GetMapping("/av1/{usuarioId}")
    public ResponseEntity<List<Produto>> listarProdutosExcetoDoUsuario(@PathVariable Long usuarioId) {
        List<Produto> produtos = produtoServices.getProdutosExcetoDoUsuario(usuarioId);
        return ResponseEntity.ok(produtos);
    }



    @PostMapping("/cadastrar_produto")
    public ResponseEntity<?> cadastrarProduto (
            @RequestParam Long usuarioId,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam String price,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        try {
            double precoConvertido = Double.parseDouble(price.replace(",", ".")); // substitui vírgula por ponto, se necessário

            Produto pdt = produtoServices.CadastrarProduto(nome, precoConvertido, descricao, imagem, usuarioId);

            return ResponseEntity.ok(pdt);

        } catch (NumberFormatException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Preço inválido: não foi possível converter '" + price + "' para número.");
        }
    }


    @GetMapping("/search/{search}")
    public ResponseEntity<?> getAllProdutos (@PathVariable String search) {

        List<Produto> produtoP3 = produtoServices.GetAllProdutos(search);

        if(produtoP3.isEmpty()){
            return  ResponseEntity.ok(produtoP3);
        }

        return ResponseEntity.ok(HttpStatus.OK).ok(produtoP3);
    }


    @PostMapping("/{produtoId}/{quantidade}/{userId}/adicionar")
    public ResponseEntity<?> adicionarProduto(
            @PathVariable Long produtoId,
            @PathVariable int quantidade, @PathVariable Long userId) {


        ItemCarrinho itemCarrinho = itemCarrinhoService.adicionarProdutoAoCarrinho(
                produtoId, quantidade, userId);
        return ResponseEntity.ok(itemCarrinho);
    }


    @GetMapping("/carrinho/{clienteId}")
    public List<ItemCarrinho> getCarrinhoDoCliente(@PathVariable Long clienteId) {
        return itemCarrinhoService.listCarrinho(clienteId);
    }


}
