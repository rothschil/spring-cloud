package xyz.wongs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.statemachine.StateMachine;
import xyz.wongs.config.eum.Events;
import xyz.wongs.config.eum.States;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs
 * @Description: TODO
 * @date 2018/6/21 16:59
 **/
@SpringBootApplication
@EnableCaching
public class StateMachineApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class, args);
    }

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }
}
