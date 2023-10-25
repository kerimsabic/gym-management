package ba.edu.ibu.gym.rest.dto;

import ba.edu.ibu.gym.core.model.Equipment;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;

import java.util.Date;
import java.util.List;

public class EquipmentRequestDTO {
    private String name;
    private String type;
    private String manufacturer;
    private String image;

    private List<Date> serviceHistory;

    public EquipmentRequestDTO(){

    }


    public EquipmentRequestDTO(Equipment equipment){
        this.name = equipment.getName();
        this.type = equipment.getType();
        this.manufacturer = equipment.getManufacturer();
        this.image = equipment.getImage();
        this.serviceHistory = equipment.getServiceHistory();
    }

    public Equipment toEntity(){
        Equipment equipment=new Equipment();
        equipment.setName(name);
        equipment.setType(type);
        equipment.setManufacturer(manufacturer);
        equipment.setImage(image);
        equipment.setServiceHistory(serviceHistory);
        return equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Date> getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(List<Date> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

}
