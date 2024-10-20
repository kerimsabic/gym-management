package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.User;
import ba.edu.ibu.gym.core.model.enums.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {

    List<Equipment> findByName(String treadmill);
}
