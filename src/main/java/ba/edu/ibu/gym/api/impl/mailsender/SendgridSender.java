package ba.edu.ibu.gym.api.impl.mailsender;

import ba.edu.ibu.gym.core.api.mailsender.MailSender;
import ba.edu.ibu.gym.core.model.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(name = "configuration.mailsender.default", havingValue = "sendgrid")
@Component
public class SendgridSender implements MailSender {
    @Override
    public String send(List<User> users, String message) {
        for(User user : users){
            System.out.println("Message sent to: "+ user.getEmail());
        }
        return "Message: "+message+"\n sent via Sendgrid";
    }

    @Override
    public String sendSpecific(User user, String message) {
        return "Message: "+message+"\n sent via Sendgrid to "+ user.getEmail();
    }
}
