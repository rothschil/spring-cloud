package xyz.wongs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud xyz.wongs
 * @Description: TODO
 * @date 2018/6/22 11:06
 **/
//@EnableDiscoveryClient
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}
