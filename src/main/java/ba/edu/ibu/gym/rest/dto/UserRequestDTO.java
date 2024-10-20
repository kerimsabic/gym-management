package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.StatusType;
import ba.edu.ibu.gym.core.model.enums.UserType;
import org.springframework.stereotype.Component;

public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
   // private UserType userType;
    private String phone;
    private String address;
    private String image;
   // private StatusType statusType;


    public  UserRequestDTO() { }

    public UserRequestDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
       // this.userType = user.getUserType();
        this.phone=user.getPhone();
        this.address=user.getAddress();
        this.image=user.getImage();
        this.username= user.getUsername();
       // this.statusType=user.getStatusType();
    }
    public User toEntity(){
        User user= new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
     //  user.setUserType(userType);
        user.setAddress(address);
        user.setPhone(phone);
        user.setImage(image);
        user.setUsername(username);
       // user.setStatusType(statusType);
        user.setStatusType(StatusType.OFFLINE);
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
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

  /*  public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }*/

   /* public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }*/
}
