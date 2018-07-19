package xyz.wongs.tools.dynamicip.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import xyz.wongs.tools.dynamicip.entity.IpAddress;
import xyz.wongs.tools.dynamicip.service.IpAddressService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip.task
 * @Description: TODO
 * @date 2018/7/3 16:05
 **/
@Component
public class PingIpAddressTask {

    private static Logger logger = LoggerFactory.getLogger(PingIpAddressTask.class);
    @Autowired
    private IpAddressService ipAddressService;


    public void pingIpAddress(){
        String type = "http";
        int pageNum = ipAddressService.getIpAddressCounts(type);
        logger.error(" 验证IP，共 "+pageNum+" 页 ");
        for (int i = 0; i <pageNum; i++) {
            Page<IpAddress> IpAddressPage = ipAddressService.findIpAddress(i,type,null);
            List<IpAddress> ipAddressList = IpAddressPage.getContent();

            startPing(ipAddressList.size(), ipAddressList);
        }
    }

    /**
     * 方法实现说明
     * @method      startPing
     * @author      WCNGS@QQ.COM
     * @version
     * @see
     * @param threadNum
     * @param ipAddressList
     * @return      void
     * @exception
     * @date        2018/7/3 16:37
     */
    public void startPing(int threadNum,List<IpAddress> ipAddressList) {

        // 创建一个线程池，多个线程同步执行
        final CountDownLatch cdAnswer = new CountDownLatch(threadNum);

        ExecutorService pool = new ThreadPoolExecutor(10, 15, 5, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t =  new Thread(r);
                        return t;
                    }
                });

//        Runnable r = ()->{};
        logger.error(" 共有 数量为 "+ipAddressList.size()+" 的IP待确认");
        for (int i = 0; i < ipAddressList.size(); i++) {
            pool.execute(new PingRunner(ipAddressList.get(i), cdAnswer));
        }

        try {
            logger.error("线程等待开始=" + cdAnswer.getCount());
            cdAnswer.await();
            logger.error("线程等待结束=" + cdAnswer.getCount());
        } catch (InterruptedException e) {
            logger.error(" 线程中断");
        }
        pool.shutdown();
        try {
            while (!pool.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PingRunner implements Runnable {
        private String host = null;
        private int port = 0;
        /**
         *  需验证的IP
         */
        private IpAddress ipAddress;
        private CountDownLatch cdAnswer;

        public PingRunner(IpAddress ipAddress, CountDownLatch cdAnswer) {
            this.ipAddress = ipAddress;
            this.cdAnswer = cdAnswer;
        }

        Socket s = new Socket();

        @Override
        public void run() {
            host = ipAddress.getIp();
            port = Integer.valueOf(ipAddress.getPort());
            SocketAddress add = new InetSocketAddress(host, port);
            try {
                // 超时3秒
                s.connect(add, 3000);
                ipAddressService.updateWeights(ipAddress);
            } catch (IOException e) {
                ipAddressService.delAddress(ipAddress);
            } finally {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("");
                } finally {
                    cdAnswer.countDown();
                }
            }
        }
    }
}
