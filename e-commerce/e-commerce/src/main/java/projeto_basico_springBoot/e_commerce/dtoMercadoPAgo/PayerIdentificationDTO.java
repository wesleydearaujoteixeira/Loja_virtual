package projeto_basico_springBoot.e_commerce.dtoMercadoPAgo;


public class PayerIdentificationDTO {


    private String type;


    private String number;

    public PayerIdentificationDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}