package xyz.wongs.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue initFirstQueue(){
        return new Queue(Cont.QUEUE_NAME);
    }


}
