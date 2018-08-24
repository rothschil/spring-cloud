package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
@EnableSwagger2
public class ZipkinProApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinProApplication.class, args);
    }

//    @Bean
//    public AlwaysSampler defaultSampler(){
//        return new AlwaysSampler();
//    }

}
