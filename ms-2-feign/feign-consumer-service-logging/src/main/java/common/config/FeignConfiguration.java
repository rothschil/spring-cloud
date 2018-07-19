package common.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud common.config
 * @Description: TODO
 * @date 2018/6/27 22:51
 **/
@Configuration
public class FeignConfiguration {

    @Bean
    public Contract feginContract(){
          return new feign.Contract.Default();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
