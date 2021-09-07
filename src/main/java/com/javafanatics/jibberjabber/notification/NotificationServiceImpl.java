package com.javafanatics.jibberjabber.notification;
import com.javafanatics.jibberjabber.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:mail.properties")
public class NotificationServiceImpl implements NotificationService {
    private JavaMailSender javaMailSender;
    private Environment environment;

    @Autowired
    public void SetJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void SetEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void sendNotification(User user, String jibber) {
        for (User follower : user.getFollowedBy()) {
            sendSimpleMessage(user.getEmail(), user.getHandle() + " posted a new Jibber!",
                    user.getHandle() + " posted a new Jibber to your feed:\n" + jibber);
        }
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
