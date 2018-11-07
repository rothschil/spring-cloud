package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import xyz.wongs.common.abs.repository.BaseRepositoryFactoryBean;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName ZoneApplication
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/28 17:28
 * @Version 1.0.0
*/
@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@ServletComponentScan(basePackages = {"xyz.wongs"}) //扫描使用注解方式的Servlet
@EnableJpaRepositories(basePackages = {"xyz.wongs"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//指定自己的工厂类
)
public class ZoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZoneApplication.class,args);
    }


}
