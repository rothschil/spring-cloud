package xyz.wongs.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @ClassName Provider
* @Description
* @author WCNGS@QQ.COMTODO
* @date 2018/8/28 16:28
* @Version 1.0.0
*/
@Component
@RabbitListener(queues = "firstQueue")
public class Consumer {


    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }
}
