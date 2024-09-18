package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;

public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private String email;
    private String username;
    private String image;
    private String address;
    private StatusType statusType;
    private String phone;


    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName=user.getLastName();
        this.userType = user.getUserType();
        this.email = user.getEmail();
        this.image=user.getImage();
        this.username=user.getUsername();
        this.statusType=user.getStatusType();
        this.address=user.getAddress();
        this.phone=user.getPhone();

    }

    public UserDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
