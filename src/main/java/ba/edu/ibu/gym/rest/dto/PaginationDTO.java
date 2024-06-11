package ba.edu.ibu.gym.rest.dto;

import java.util.List;

public class PaginationDTO {

    private List<MemberDTO> members;
    private int totalPages;

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginationDTO(){

    }

    public PaginationDTO(List<MemberDTO> members, int totalPages){
        this.totalPages=totalPages;
        this.members=members;
    }
}
