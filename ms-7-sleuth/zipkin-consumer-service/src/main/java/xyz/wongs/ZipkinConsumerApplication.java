package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
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
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
public class ZipkinConsumerApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZipkinConsumerApplication.class, args);
    }

}
