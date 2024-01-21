package ba.edu.ibu.gym.rest.dto;

public class MessageDTO {

    private String message;

    public MessageDTO(){}

    public MessageDTO(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
