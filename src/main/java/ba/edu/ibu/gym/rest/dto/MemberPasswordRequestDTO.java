package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Member;

public class MemberPasswordRequestDTO {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MemberPasswordRequestDTO() {
    }

    public MemberPasswordRequestDTO(String password) {

        this.password = password;
    }

    public String toEntity() {
        return getPassword();

    }
}
