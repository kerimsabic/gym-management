package ba.edu.ibu.gym.rest.controllers;


import ba.edu.ibu.gym.core.model.Attendance;
import ba.edu.ibu.gym.core.service.AttendanceService;
import ba.edu.ibu.gym.rest.dto.AttendanceDTO;
import ba.edu.ibu.gym.rest.dto.AttendanceRequestDTO;
import ba.edu.ibu.gym.rest.dto.MembershipDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    @GetMapping("/api/attendance/date")
    public List<AttendanceDTO> getAttendanceByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Date startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return attendanceService.getAttendanceByDate(startDate, endDate);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable String id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "memberAttendance/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByMemberId(@PathVariable String id) {
        return ResponseEntity.ok(attendanceService.getAttendanceByMemberId(id));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AttendanceDTO> recordAttendance(@RequestBody AttendanceRequestDTO payload) {
        return ResponseEntity.ok(attendanceService.recordAttendance(payload));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String id){
        attendanceService.deleteAttendance(id);
        return null;
    }
}
