package xyz.wongs.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.domain.User;
import xyz.wongs.service.ConsumerFeignClient;

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

    @Autowired
    private ConsumerFeignClient consumerFeignClient;

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
    public List<User> getUserList() {

        return consumerFeignClient.getUserList();
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
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        consumerFeignClient.postUser(user);
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
    public User getUser(@PathVariable Long id) {

        return consumerFeignClient.getUser(id);
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

        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = consumerFeignClient.getUser(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        consumerFeignClient.postUser(u);
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
        // 处理"/users/{id}"的DELETE请求，用来删除User
        consumerFeignClient.deleteUser(id);
        return "success";
    }
}
