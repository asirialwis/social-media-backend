package org.chat.messagingweb;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaAuditing
public class MessagingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagingWebApplication.class, args);
    }

}
