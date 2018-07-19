package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud xyz.wongs
 * @Description: TODO
 * @date 2018/6/22 11:06
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class RibbonConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumerServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate  restTemplate(){
         return new RestTemplate();
    }

}
