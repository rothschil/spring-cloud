package xyz.wongs.web;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.domain.User;
import xyz.wongs.domain.UserRepository;
import xyz.wongs.jwt.interf.PassToken;
import xyz.wongs.jwt.interf.UserLoginToken;
import xyz.wongs.service.TokenService;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.web
 * @Description: TODO
 * @date 2018/6/21 8:49
 **/
@Api(description="",value="user")
@RequestMapping(value = "/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    @ApiOperation(value = "登陆用户", notes = "登陆用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PassToken
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody User user){
        JSONObject jsonObject=new JSONObject();
        User userForBase=userRepository.getOne(user.getId());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getName().equals(user.getName())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }

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
    public List<User> getUserList() {

        // 处理"/users/"的GET请求，用来获取用户列表
        List<User> users = userRepository.findAll();
        return users;
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
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
    public String postUser(@ModelAttribute User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        userRepository.save(user);
        return "success";
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {

        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return userRepository.findOne(id);
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {

        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = userRepository.findOne(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        userRepository.saveAndFlush(u);
        return "success";
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        userRepository.delete(id);
        return "success";
    }

    @RequestMapping(value = "/list-all", method = RequestMethod.GET)
    public void initDatas() {
        User user = new User("李四", 20);
        userRepository.saveAndFlush(user);
        user = new User("王五", 87);
        userRepository.saveAndFlush(user);
    }
}
