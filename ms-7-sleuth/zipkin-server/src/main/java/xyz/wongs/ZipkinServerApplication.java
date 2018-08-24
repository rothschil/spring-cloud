package xyz.wongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
* @ClassName ZipkinServerApplication
* @Description 
* @author WCNGS@QQ.COMTODO
* @date 2018/8/24 9:23
* @Version 1.0.0
*/
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }
}
