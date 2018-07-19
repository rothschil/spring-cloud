package xyz.wongs.service;

import common.config.FeignConfiguration;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
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
@FeignClient(value = "provider-service",configuration =FeignConfiguration.class )
public interface ProviderService {


    @RequestLine("GET /user/")
    List<User> getUserList();

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("POST /user/")
    String postUser(@RequestBody User user);

    @RequestLine("GET /users/{id}")
    User getUser(@Param("id") Long id);

    @RequestLine("PUT /users/{id}")
    String putUser(@Param("id") Long id, @ModelAttribute User user);

    @RequestLine("DELETE /users/{id}")
    String deleteUser(@Param("id") Long id);
}
