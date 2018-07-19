package common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud common.config
 * @Description: TODO
 * @date 2018/6/28 19:58
 **/
@Configuration
public class FeignClient2LoggingConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
