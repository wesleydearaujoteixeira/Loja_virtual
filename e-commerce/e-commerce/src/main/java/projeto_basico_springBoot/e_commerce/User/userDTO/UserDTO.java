package projeto_basico_springBoot.e_commerce.User.userDTO;

public class UserDTO {

    private Long id;
        private String email;
        private String username;
    public UserDTO (Long id, String email) {

        this.id = id;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
