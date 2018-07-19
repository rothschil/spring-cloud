package xyz.wongs.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:  使用@Async实现异步调用
 * @Package spring-boot xyz.wongs.async
 * @Description: TODO
 * @date 2018/6/21 11:06
 **/
//@Slf4j
@Component
public class AsyncTaskByCustomizeThreadPool {

    public static Random random =new Random();
    //记得配置log4j.properties ,的命令行输出水平是debug
    protected Logger log= LoggerFactory.getLogger(AsyncTaskByCustomizeThreadPool.class);

    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        log.info("Begin Task One");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("Task One is End, cost times "+(end-start));
    }


    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        log.info("Begin Task Two");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("Task Two is End, cost times "+(end-start));
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        log.info("Begin Task Three");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("Task Three is End, cost times "+(end-start));
    }
}
