//package xyz.wongs.async;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//import org.springframework.stereotype.Component;
//
//import java.util.Random;
//import java.util.concurrent.Future;
//
///**
// * @author WCNGS@QQ.CO
// * @version V1.0
// * @Title:  使用@Async实现异步调用
// * @Package spring-boot xyz.wongs.async
// * @Description: TODO
// * @date 2018/6/21 11:06
// **/
//@Component
//public class AsyncTask {
//
//    public static Random random =new Random();
//    //记得配置log4j.properties ,的命令行输出水平是debug
//    protected Logger logger= LoggerFactory.getLogger(AsyncTask.class);
//
//    @Async
//    public Future<String> doTaskOne() throws Exception {
//        logger.info("Begin Task One");
//        long start = System.currentTimeMillis();
//        Thread.sleep(random.nextInt(10000));
//        long end = System.currentTimeMillis();
//        logger.info("Task One is End, cost times "+(end-start));
//        return new AsyncResult<>(" Task One Worked");
//    }
//
//
//    @Async
//    public Future<String> doTaskTwo() throws Exception {
//        logger.info("Begin Task Two");
//        long start = System.currentTimeMillis();
//        Thread.sleep(random.nextInt(10000));
//        long end = System.currentTimeMillis();
//        logger.info("Task Two is End, cost times "+(end-start));
//        return new AsyncResult<>(" Task Two Worked");
//    }
//
//    @Async
//    public Future<String> doTaskThree() throws Exception {
//        logger.info("Begin Task Three");
//        long start = System.currentTimeMillis();
//        Thread.sleep(random.nextInt(10000));
//        long end = System.currentTimeMillis();
//        logger.info("Task Three is End, cost times "+(end-start));
//        return new AsyncResult<>(" Task Three Worked");
//    }
//}
