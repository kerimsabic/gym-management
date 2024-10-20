package ba.edu.ibu.gym.rest.controllers;

import ba.edu.ibu.gym.core.model.Trainer;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.service.PaymentService;
import ba.edu.ibu.gym.rest.dto.ImageDTO;
import ba.edu.ibu.gym.rest.dto.PaymentRequest;
import ba.edu.ibu.gym.rest.dto.PaymentResponse;
import ba.edu.ibu.gym.rest.dto.PlanRequestDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/payment")
@SecurityRequirement(name = "JWT Security")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @RequestMapping(method = RequestMethod.POST, path = "/payment")
    public ResponseEntity<PaymentResponse> crateOrder(@RequestBody PaymentRequest req) throws IOException, GeneralSecurityException, StripeException {
        PaymentResponse res = paymentService.createPaymentLink(req);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, path = "/paymentOnRegister")
    public ResponseEntity<PaymentResponse> crateOrderRegister(@RequestBody PaymentRequest req) throws IOException, GeneralSecurityException, StripeException {
        PaymentResponse res = paymentService.createPaymentLinkWhenRegister(req);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }


}
