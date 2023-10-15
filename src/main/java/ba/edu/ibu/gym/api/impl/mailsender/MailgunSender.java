package ba.edu.ibu.gym.api.impl.mailsender;

import ba.edu.ibu.gym.core.api.mailsender.MailSender;
import ba.edu.ibu.gym.core.model.Member;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(name = "configuration.mailsender.default", havingValue = "mailgun")
@Component
public class MailgunSender implements MailSender {
    @Override
    public String send(List<Member> members, String message) {
        for (Member member:members){
            System.out.println("Message sent to: "+member.getEmail());
        }
        return "Message: "+message+"\n sent via Mailgun";
    }

    @Override
    public String sendSpecific(Member member, String message) {
        return "Message: "+message+"\n sent via Mailgun to "+member.getEmail();
    }
}
