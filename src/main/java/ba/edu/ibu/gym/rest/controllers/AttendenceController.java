package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.service.AttendanceService;
import ba.edu.ibu.gym.rest.dto.AttendenceRequestDTO;
import ba.edu.ibu.gym.rest.dto.UserDTO;
import ba.edu.ibu.gym.rest.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/attendence")
public class AttendenceController {
    private final AttendanceService attendanceService;

    public AttendenceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<Attendance>> getAll() {
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Attendance> getAttendenceById(@PathVariable String id) {
        return ResponseEntity.ok(attendanceService.getAttendenceById(id));
    }

    @RequestMapping(method = RequestMethod.POST,path = "/add")
    public ResponseEntity<Attendance> createAttendence(@RequestBody AttendenceRequestDTO attendence){
        return ResponseEntity.ok(attendanceService.createAttendance(attendence));
    }

    /*@RequestMapping(method = RequestMethod.PUT,path = "/{id}")
    public ResponseEntity<Attendance> updateAttendence(@PathVariable String id,@RequestBody AttendenceRequestDTO attendence){
        return ResponseEntity.ok(attendanceService.up(id,user));
    }*/

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<Void> deleteAttenence(@PathVariable String id){
        attendanceService.deleteAttendence(id);
        return null;
    }
}
