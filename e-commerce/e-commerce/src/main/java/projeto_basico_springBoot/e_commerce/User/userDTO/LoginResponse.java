package projeto_basico_springBoot.e_commerce.User.userDTO;

public class LoginResponse {

    private Long id;
    private String token;
    private String username;

    public LoginResponse(Long id, String token, String username) {
        this.token = token;
        this.username = username;
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
