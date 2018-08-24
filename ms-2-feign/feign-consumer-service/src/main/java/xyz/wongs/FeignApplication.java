package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

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
@EnableFeignClients
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
