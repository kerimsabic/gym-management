package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentRepository extends MongoRepository<Equipment,String> {
}
