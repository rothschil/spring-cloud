package xyz.wongs.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xyz.wongs.domain.User;

import java.util.Arrays;
import java.util.List;

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

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 方法实现说明
     * @method      getUserList
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param
     * @return      java.util.List<xyz.wongs.domain.User>
     * @exception
     * @date        2018/6/21 8:53
     */
    @ApiOperation(value="获取用户列表", notes="获取全部用户信息")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getUserList() {

        return restTemplate.getForEntity("http://PROVIDER-SERVICE/users/",String.class).getBody();
    }

    /**
     * 方法实现说明
     * @method      postUser
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param user
     * @return      java.lang.String
     * @exception
     * @date        2018/6/21 8:53
     */
    @ApiOperation(value="新增用户", notes="新增基本用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value="/", method=RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {

        return "success";
    }

    /**
     * 方法实现说明
     * @method      getUser
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param id
     * @return      xyz.wongs.domain.User
     * @exception
     * @date        2018/6/21 8:55
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable Long id) {
        return restTemplate.getForEntity("http://PROVIDER-SERVICE/users/"+id,String.class).getBody();
    }

    /**
     * 方法实现说明
     * @method      putUser
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param id
     * @param user
     * @return      java.lang.String
     * @exception
     * @date        2018/6/21 8:55
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {


        return "success";
    }


    /**
     * 方法实现说明
     * @method      deleteUser
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param id
     * @return      java.lang.String
     * @exception
     * @date        2018/6/21 8:56
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {

        return "success";
    }


    @RequestMapping(value="/list-all", method=RequestMethod.GET)
    public List<User> getUserByList() {
        User[] users = this.restTemplate.getForObject("http://PROVIDER-SERVICE/users/",User[].class);

        List<User> userList = Arrays.asList(users);
        for(User u :userList) {
            logger.error(u.toString());
        }
        return userList;
    }
}
