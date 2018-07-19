package xyz.wongs.tools.zonecode.service.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wongs.tools.zonecode.ZoneCodeStringUtils;
import xyz.wongs.ZoneCodeApplication;
import xyz.wongs.tools.zonecode.entity.Location;
import xyz.wongs.tools.zonecode.service.LocationService;
import xyz.wongs.tools.zonecode.service.ProcessService;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@SpringBootTest(classes ={ZoneCodeApplication.class})
public class ProcessServiceImplTest {
    private static final String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";


    @Autowired
    @Qualifier("processService")
    private ProcessService processService;

    @Autowired
    private LocationService locationService;

    @Test
    public void getHTML() throws Exception {

        List<Location> root = locationService.getLocationListByLevel(0);
        for (Location location: root) {
            String uls =  url+location.getUrl();
            processService.getHTML(uls,location);
        }
    }


    @Test
    public void initSecondLevelResolve() throws Exception {
        int totalPages = locationService.getLocationCountsByLevel(1);

        for (int i = 1; i <= totalPages; i++) {
            secondLevelResolve(i);
        }
    }


    public void secondLevelResolve(int pageNumber) throws Exception {
        //初始化0
        Page<Location> pageLocation = locationService.getLocationsByLevel(1,pageNumber);
        Iterator<Location> iter = pageLocation.iterator();
        while(iter.hasNext()){
            Location location = iter.next();
            String url2 = new StringBuilder().append(url).append(location.getUrl()).toString();
            processService.getHTML2(url2,location);
            locationService.updateLocationFlag("Y",location.getId());
        }

        int times = new Random().nextInt(10000);
        Thread.sleep(times);
    }


    @Test
    public void intiThridLevelResolve(){

        int totalPages = locationService.getLocationCountsByLevel(2);
        int f = 1;
        for (int i = 1; i <= totalPages; i++) {
            f++;
            thridLevelResolve("c",i);

            if(f==10){
               break;
            }
        }
    }

//    @Async
//    @Test
//    public void intiThridLevelResolve(){
//        int totalPages = locationService.getLocationCountsByLevel(2);
//        ExecutorService fixedThreadPool = new ThreadPoolExecutor(10, 15, 5, TimeUnit.SECONDS, new SynchronousQueue<>(),
//                new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        Thread t =  new Thread(r);
//                        System.out.println("生成线程 "+t);
//                        return t;
//                    }
//                });
//
////        Runnable r = ()->{};
//
//        for (int i = 1; i <= totalPages; i++) {
//            int ii = i;
//            fixedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName()+" 在忙...");
////                    thridLevelResolve(ii);
//                    Page<Location> pageLocation = locationService.getLocationsByLevel(2,ii);
//
//                    Iterator<Location> iter = pageLocation.iterator();
//                    while(iter.hasNext()){
//                        Location location = iter.next();
//                        String url2 = new StringBuilder().append(url).append(StringUtils.getUrlStrByLocationCode(location.getLocalCode(),2)).append(location.getUrl()).toString();
//                        processService.thridLevelResolve(url2,location);
//
//                    }
//                    try {
//                        int times = new Random().nextInt(30000);
//                        Thread.sleep(times);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//        }
//    }

    public void thridLevelResolve(String flag,int pageNumber) {
        Page<Location> pageLocation = locationService.getLocationsByLevel(2, pageNumber);

        Iterator<Location> iter = pageLocation.iterator();
        while (iter.hasNext()) {
            Location location = iter.next();
            String url2 = new StringBuilder().append(url).append(ZoneCodeStringUtils.getUrlStrByLocationCode(location.getLocalCode(), 2)).append(location.getUrl()).toString();
            processService.thridLevelResolve(url2, location, flag);
            try {
                int times = new Random().nextInt(2000);
                Thread.sleep(times);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}