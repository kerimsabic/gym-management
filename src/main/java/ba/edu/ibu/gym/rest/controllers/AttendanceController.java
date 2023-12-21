package ba.edu.ibu.gym.rest.controllers;


import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.service.AttendanceService;
import ba.edu.ibu.gym.rest.dto.AttendanceDTO;
import ba.edu.ibu.gym.rest.dto.AttendanceRequestDTO;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/attendance")
@SecurityRequirement(name = "JWT Security")
public class AttendanceController {

    private AttendanceService attendanceService;


    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable String id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AttendanceDTO> recordAttendance(@RequestBody AttendanceRequestDTO payload) {
        return ResponseEntity.ok(attendanceService.recordAttendance(payload));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAttendance(@RequestParam String id){
        attendanceService.deleteAttendance(id);
        return null;
    }
}
