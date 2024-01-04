package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Equipment;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.service.EquipmentService;
import ba.edu.ibu.gym.core.service.JwtService;
import ba.edu.ibu.gym.core.service.TrainingPlanService;
import ba.edu.ibu.gym.core.service.UserService;
import ba.edu.ibu.gym.rest.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(EquipmentController.class)
@Import(SecurityConfiguration.class)
public class EquipmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EquipmentService equipmentService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @Test
    public void shoudReturnAllTrainingPlans() throws Exception {

        Equipment equipment= new Equipment();
        equipment.setManufacturer("Test123");
        equipment.setType("bench press");
        equipment.setId("testId");
        equipment.setName("someTest name");

        Mockito.when(equipmentService.getAllEquipment()).thenReturn(List.of(equipment));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/equipment/").contentType(MediaType.APPLICATION_JSON)).andReturn();
        String response = result.getResponse().getContentAsString();
        System.out.println(response);


    }
}
