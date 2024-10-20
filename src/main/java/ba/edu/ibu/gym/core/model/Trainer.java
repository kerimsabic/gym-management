package ba.edu.ibu.gym.core.model;

import ba.edu.ibu.gym.core.model.enums.UserType;
import ba.edu.ibu.gym.rest.dto.MemberDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Trainer extends User{


    /*private List<Member> members;



    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

  //  public UserType getUserType() {
      //  return userType;
    //}

    public void removeMember(Member member) {
        if (getMembers() != null) {
            members.remove(member);
        }
    }*/


}
