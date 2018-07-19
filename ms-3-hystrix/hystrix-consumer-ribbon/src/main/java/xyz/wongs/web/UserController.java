package xyz.wongs.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xyz.wongs.domain.User;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.web
 * @Description: TODO
 * @date 2018/6/21 8:49
 **/
@RequestMapping(value = "/users")
@RestController
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    private String url = "http://PROVIDER-SERVICE/users/";

    @Autowired
    public RestTemplate restTemplate;

    /**
     * 方法实现说明
     *
     * @param
     * @return java.util.List<xyz.wongs.domain.User>
     * @throws
     * @method getUserList
     * @author WCNGS@QQ.COM
     * @version
     * @date 2018/6/21 8:53
     * @see
     */
    @ApiOperation(value = "获取用户列表", notes = "获取全部用户信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getUserListError")
    public String getUserList() {
        return restTemplate.getForEntity(url, String.class).getBody();
    }

    public String getUserListError() {
        return "error";
    }

    /**
     * 方法实现说明
     *
     * @param user
     * @return java.lang.String
     * @throws
     * @method postUser
     * @author WCNGS@QQ.COM
     * @version
     * @date 2018/6/21 8:53
     * @see
     */
    @ApiOperation(value = "新增用户", notes = "新增基本用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "postUserError")
    public String postUser(@ModelAttribute User user) {
        restTemplate.postForEntity(url, user, User.class);
        return "success";
    }

    public String postUserError(User user) {

        return user.toString();
    }

    /**
     * 方法实现说明
     *
     * @param id
     * @return xyz.wongs.domain.User
     * @throws
     * @method getUser
     * @author WCNGS@QQ.COM
     * @version
     * @date 2018/6/21 8:55
     * @see
     */
    @HystrixCommand(fallbackMethod = "getUserFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    @HystrixProperty(name="execution.isolation.strategy",value = "THREAD")}
                    )
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        log.error(" Request: user id is "+ id);
        return restTemplate.getForObject(url+id, User.class);
    }

    public User getUserFallback(Long id) {
        User user = new User("",0);
        user.setId(0L);
        return user;
    }

    /**
     * 方法实现说明
     *
     * @param id
     * @param user
     * @return java.lang.String
     * @throws
     * @method putUser
     * @author WCNGS@QQ.COM
     * @version
     * @date 2018/6/21 8:55
     * @see
     */
    @HystrixCommand(fallbackMethod = "putUserFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.timeout.enabled", value = "false")})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {

        User u = restTemplate.getForObject(url + id, User.class);
        u.setName(user.getName());
        u.setAge(user.getAge());
        restTemplate.postForEntity(url, u, User.class);
        return "success";
    }

    public String putUserFallback(Long id) {
        String fail="暂时不能提供服务";
        return fail;
    }

    /**
     * 方法实现说明
     *
     * @param id
     * @return java.lang.String
     * @throws
     * @method deleteUser
     * @author WCNGS@QQ.COM
     * @version
     * @date 2018/6/21 8:56
     * @see
     */
    @HystrixCommand(fallbackMethod = "userFallback")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        restTemplate.delete(url, id);
        return "success";
    }


    public String userFallback(Long id) {
        String fail="暂时不能提供服务";
        return fail;
    }

}
