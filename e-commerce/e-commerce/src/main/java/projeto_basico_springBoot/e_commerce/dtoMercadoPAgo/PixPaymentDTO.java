package projeto_basico_springBoot.e_commerce.dtoMercadoPAgo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PixPaymentDTO {

    private BigDecimal transactionAmount;

    @JsonProperty("description")
    private String productDescription;

    private PayerDTO payer;

    public PixPaymentDTO() {
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public PayerDTO getPayer() {
        return payer;
    }

    public void setPayer(PayerDTO payer) {
        this.payer = payer;
    }
}