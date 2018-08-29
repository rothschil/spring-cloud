package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @ClassName RabbitMqApplication
* @Description 
* @author WCNGS@QQ.COMTODO
* @date 2018/8/28 16:25
* @Version 1.0.0
*/
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
