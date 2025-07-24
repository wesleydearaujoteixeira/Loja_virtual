package projeto_basico_springBoot.e_commerce.PixPaymentController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto_basico_springBoot.e_commerce.dtoMercadoPAgo.PixPaymentDTO;
import projeto_basico_springBoot.e_commerce.dtoMercadoPAgo.PixPaymentResponseDTO;
import projeto_basico_springBoot.e_commerce.servicesMercadoPAGO.PixPaymentService;



@RestController
@RequestMapping("/process_payment")
public class PixPaymentController {
    private final PixPaymentService pixPaymentService;

    @Autowired
    public PixPaymentController(PixPaymentService pixPaymentService) {
        this.pixPaymentService = pixPaymentService;
    }

    @PostMapping
    public ResponseEntity<PixPaymentResponseDTO> processPayment(@RequestBody PixPaymentDTO pixPaymentDTO) {
        PixPaymentResponseDTO payment = pixPaymentService.processPayment(pixPaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}