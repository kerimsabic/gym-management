package ba.edu.ibu.gym.core.api.mailsender;

import ba.edu.ibu.gym.core.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface MailSender {
    public String send(List<Member> members, String message);
    public String sendSpecific(Member member, String message);

}
