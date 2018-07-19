package xyz.wongs.service;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.wongs.domain.User;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.service
 * @Description: TODO
 * @date 2018/7/5 20:38
 **/
@Component
public class ConsumerFeignClientFallbackFactory implements FallbackFactory<ConsumerFeignClient>{

    private static Logger logger = LoggerFactory.getLogger(ConsumerFeignClientFallbackFactory.class);


    @Override
    public ConsumerFeignClient create(Throwable throwable) {

        return new ConsumerFeignClient(){
            @Override
            public List<User> getUserList() {
                return null;
            }

            @Override
            public String postUser(User user) {
                return null;
            }

            @Override
            public User getUser(Long id) {
                logger.error(" fallback; reason was: " + throwable.getMessage());
                User user = new User();
                user.setId(-1L);
                user.setName("sell");
                return user;
            }

            @Override
            public String putUser(Long id, User user) {
                return null;
            }

            @Override
            public String deleteUser(Long id) {
                return null;
            }
        };
    }
}
