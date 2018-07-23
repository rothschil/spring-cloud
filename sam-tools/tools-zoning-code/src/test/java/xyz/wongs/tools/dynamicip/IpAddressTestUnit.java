package xyz.wongs.tools.dynamicip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wongs.ZoneCodeApplication;
import xyz.wongs.tools.dynamicip.entity.IpAddress;
import xyz.wongs.tools.dynamicip.service.IpAddressService;
import xyz.wongs.tools.dynamicip.task.IpAddressTask;
import xyz.wongs.tools.dynamicip.task.PingIpAddressTask;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip
 * @Description: TODO
 * @date 2018/7/3 14:20
 **/
@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@SpringBootTest(classes ={ZoneCodeApplication.class})
public class IpAddressTestUnit {

    @Autowired
    private IpAddressService ipAddressService;

    @Autowired
    private IpAddressTask ipAddressTask;

    @Autowired
    private PingIpAddressTask pingIpAddressTask;


    @Test
    public void testIpAddress(){
    }

    @Test
    public void testIpAddressByUrl(){
        String url = "http://www.data5u.com/";
        ipAddressTask.saveIpAddress(url);
    }

    @Test
    public void testIpAddressTimeOut(){
        testIpAddressByUrl();
        pingIpAddressTask.pingIpAddress();
    }

    @Test
    public void testGetCount(){
//        System.out.println(ipAddressService.count());
        List<IpAddress> ipAddressList = ipAddressService.findIpAddressByType("https");
        for (IpAddress ipAddress:ipAddressList){
            System.out.println(ipAddress.toString());
        }
    }




}
