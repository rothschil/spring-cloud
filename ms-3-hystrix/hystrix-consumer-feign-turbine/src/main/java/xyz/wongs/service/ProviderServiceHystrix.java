package xyz.wongs.service;

import org.springframework.stereotype.Component;
import xyz.wongs.domain.User;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud xyz.wongs.service
 * @Description: TODO
 * @date 2018/6/28 9:33
 **/
@Component
public class ProviderServiceHystrix implements ProviderService {

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
        User user = new User();
        user.setId(0L);
        user.setName("admin");
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
}
