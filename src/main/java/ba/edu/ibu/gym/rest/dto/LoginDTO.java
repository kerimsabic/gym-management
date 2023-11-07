package ba.edu.ibu.gym.rest.dto;

public class LoginDTO {

    private String jwt;


    public LoginDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
