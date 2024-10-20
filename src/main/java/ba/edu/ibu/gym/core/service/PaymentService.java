package ba.edu.ibu.gym.core.service;

import ba.edu.ibu.gym.core.model.Member;
import ba.edu.ibu.gym.core.model.TrainingPlan;
import ba.edu.ibu.gym.core.repository.MemberRepository;
import ba.edu.ibu.gym.core.repository.TrainingPlanRepository;
import ba.edu.ibu.gym.rest.dto.PaymentRequest;
import ba.edu.ibu.gym.rest.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Value("${STRIPE_APIKEY}")
    private String stripeSecretKey;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    public PaymentResponse createPaymentLink(PaymentRequest req) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String successUrl = String.format(
                "https://diplomski.gymversion2.pages.dev/payment-success?user_id=%s&training_plan_id=%s&num_of_months=%d",
                req.getUserId(), // Ensure this is part of the PaymentRequest
                req.getTrainingPlanId(), // Ensure this is part of the PaymentRequest
                req.getNumOfMonths()
        );

        Optional<Member> member = memberRepository.findById(req.getUserId());
        Optional<TrainingPlan> trainingPlan = trainingPlanRepository.findById(req.getTrainingPlanId());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl("https://diplomski.gymversion2.pages.dev/home")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(Long.valueOf(req.getPrice())*100 * req.getNumOfMonths())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Training Plan: " + req.getName())
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        PaymentResponse res = new PaymentResponse();
        res.setPaymentUrl(session.getUrl());
        emailService.sendEmail("kerim.sabic@stu.ibu.edu.ba", member.get().getFirstName(), "New membership payment confirmation", "Your memebrship has been successfully payed! New training plan: " +trainingPlan.get().getName(), "Your memebrship has been successfully payed!");

        System.out.println(res.getPaymentUrl());
        return res;

    }

    public PaymentResponse createPaymentLinkWhenRegister(PaymentRequest req) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String successUrl = String.format(
                "http://localhost:5173/login",
                req.getUserId(), // Ensure this is part of the PaymentRequest
                req.getTrainingPlanId(), // Ensure this is part of the PaymentRequest
                req.getNumOfMonths()
        );

        Optional<Member> member = memberRepository.findById(req.getUserId());
        Optional<TrainingPlan> trainingPlan = trainingPlanRepository.findById(req.getTrainingPlanId());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl("http://localhost:5173/home")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(Long.valueOf(req.getPrice())*100 * req.getNumOfMonths())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Training Plan: " + req.getName())
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        PaymentResponse res = new PaymentResponse();
        res.setPaymentUrl(session.getUrl());
        emailService.sendEmail("kerim.sabic@stu.ibu.edu.ba", member.get().getFirstName(), "New membership payment confirmation", "Your memebrship has been successfully payed! New training plan: " +trainingPlan.get().getName(), "Your memebrship has been successfully payed!");

        System.out.println(res.getPaymentUrl());
        return res;

    }
}

