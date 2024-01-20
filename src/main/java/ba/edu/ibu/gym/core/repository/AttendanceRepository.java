package ba.edu.ibu.gym.core.repository;

import ba.edu.ibu.gym.core.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository  extends MongoRepository<Attendance, String> {
    List<Attendance> findByAttendanceDateBetween(Date startDate, Date endDate);
}
