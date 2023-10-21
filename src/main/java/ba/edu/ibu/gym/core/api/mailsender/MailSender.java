package ba.edu.ibu.gym.core.api.mailsender;

import ba.edu.ibu.gym.core.model.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface MailSender {
    public String send(List<User> users, String message);
    public String sendSpecific(User user, String message);

}
