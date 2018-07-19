package xyz.wongs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package ms-cloud common.config
 * @Description: TODO
 * @date 2018/6/27 22:51
 **/
@FeignClient(name = "xx",url = "http://localhost:18888/")
public interface FeignClient2 {


    @RequestMapping("/eureka/apps/{serviceName}")
    String findServiceFromEurekaByServiceName(@PathVariable("serviceName") String serviceName);
}
