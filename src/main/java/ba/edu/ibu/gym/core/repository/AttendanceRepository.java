package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository  extends MongoRepository<Attendance, String> {

}
