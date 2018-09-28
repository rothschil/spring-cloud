package xyz.wongs.config;

import org.springframework.amqp.core.AmqpTemplate;
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
public class Provider {

    @Autowired
    public AmqpTemplate rabbitmq;

    public void sendToQueue() throws Exception{
        for (int i=0;i<10;i++){
            String content = new SimpleDateFormat("YYYY-MM-DD hh:mm:SS").format(new Date()) + " is sender!";
            System.out.println(content);
            this.rabbitmq.convertAndSend(Cont.QUEUE_NAME,content);
            Thread.sleep(200L);
        }

    }
}
