package xyz.wongs.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.wongs.domain.User;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud xyz.wongs.service
 * @Description: TODO
 * @date 2018/6/27 21:28
 **/
@FeignClient("provider-service")
public interface ProviderService {

    /**
     * 方法实现说明
     * @method
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param
     * @return
     * @exception
     * @date        2018/6/27 21:31
     */
    @RequestMapping(value = "/users/",method = RequestMethod.GET)
    List<User> getUserList();


    @RequestMapping(value="/users/", method=RequestMethod.POST)
    String postUser(@RequestBody User user);

    @RequestMapping(value="/users/{id}", method=RequestMethod.GET)
    User getUser(@PathVariable("id") Long id);


    @RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
    String putUser(@PathVariable("id") Long id, @ModelAttribute User user);

    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    String deleteUser(@PathVariable("id") Long id);
}
