package projeto_basico_springBoot.e_commerce.PedidosController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto_basico_springBoot.e_commerce.Pedidos.PedidoStatusUpdateDTO;
import projeto_basico_springBoot.e_commerce.Pedidos.Pedidos;
import projeto_basico_springBoot.e_commerce.Pedidos.PedidosServices.ItemRequest;
import projeto_basico_springBoot.e_commerce.Pedidos.PedidosServices.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidoService pedidoService;

    // Criar novo pedido
    @PostMapping("/{clienteId}")
    public ResponseEntity<?> criarPedido(
            @PathVariable Long clienteId,
            @RequestBody List<ItemRequest> itens) {
        try {
            Pedidos novoPedido = pedidoService.criarPedido(clienteId, itens);
            return ResponseEntity.ok(novoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar pedidos de um cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedidos>> buscarPedidosDoCliente(@PathVariable Long clienteId) {
        List<Pedidos> pedidos = pedidoService.buscarPedidosDoCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    // Deletar pedido (se pertence ao cliente)
    @DeleteMapping("/{pedidoId}/{clienteId}")
    public ResponseEntity<?> deletarPedido(
            @PathVariable Long pedidoId,
            @PathVariable Long clienteId) {
        try {
            pedidoService.deletarPedido(pedidoId, clienteId);
            return ResponseEntity.ok("Pedido deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PatchMapping("/{id}/{userId}/status")
    public ResponseEntity<?> atualizarStatusPedido(
            @PathVariable Long id,
            @RequestBody PedidoStatusUpdateDTO dto,
            @PathVariable Long userId
    ) {
        try {
            Pedidos pedidoAtualizado = pedidoService.atualizarStatus(id, dto.getStatus(), userId);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}
