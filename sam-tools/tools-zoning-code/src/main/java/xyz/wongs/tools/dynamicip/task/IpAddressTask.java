package xyz.wongs.tools.dynamicip.task;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wongs.basic.common.exception.NoIpAddressException;
import xyz.wongs.tools.dynamicip.entity.IpAddress;
import xyz.wongs.tools.dynamicip.service.IpAddressService;
import xyz.wongs.tools.utils.HttpCrawlerUtil;
import xyz.wongs.tools.zonecode.ZoneCodeStringUtils;

import java.io.*;
import java.util.*;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip.task.IpAddressTask
 * @Description: TODO
 * @date 2018/7/2 14:45
 **/
@Component("ipAddressTask")
public class IpAddressTask {

    @Autowired
    private IpAddressService ipAddressService;

    public void saveIpAddress(String url){
        Map<String, Object> result  = getIpAddress(url);
        Object obj = result.get(ZoneCodeStringUtils.RESULT_KEY_FLAG);
        if(null== obj) {
            List<IpAddress> ipAddresses = (List<IpAddress>)result.get(ZoneCodeStringUtils.RESULT_KEY);
            for (IpAddress ip:ipAddresses){
                ipAddressService.save(ip);
            }
        } else {
            //根据异常情况 在做处理，例如链接超时类，就要不断换IP.....待补充
        }
    }

    public Map<String, Object> getIpAddress(String url){
        Map<String, Object> map = new HashMap<String, Object>(2);
        try {
            Document doc = HttpCrawlerUtil.getDocument(url);
            Elements es = doc.select("ul.l2");
            List<IpAddress> ipAddressList = new ArrayList<IpAddress>(20);
            for (Element element : es) {
                Elements els = element.select("li");
                int size = els.size();
                IpAddress ipAddress = new IpAddress(els.get(0).text(),Integer.parseInt(els.get(1).text()),
                        els.get(2).text(),els.get(3).text(),els.get(4).text(),els.get(5).text(),els.get(6).text(),els.get(7).text(),els.get(8).text());
                ipAddressList.add(ipAddress);
            }
            map.put(ZoneCodeStringUtils.RESULT_KEY,ipAddressList);

        } catch (NoIpAddressException e) {
            map.put(ZoneCodeStringUtils.RESULT_KEY_FLAG, new Random().nextInt(20)*10);
        } catch (ConnectTimeoutException e) {
            map.put(ZoneCodeStringUtils.RESULT_KEY_FLAG, -1);
        } catch (IOException e) {
            map.put(ZoneCodeStringUtils.RESULT_KEY_FLAG, new Random().nextInt(3));
        }
        return map;
    }


    public Document getDocument(String path){
        String xmlString;
        byte[] strBuffer = null;
        int flen = 0;
        File xmlfile = new File(path);
        try {
            InputStream in = new FileInputStream(xmlfile);
            flen = (int)xmlfile.length();
            strBuffer = new byte[flen];
            in.read(strBuffer, 0, flen);

            //构建String时，可用byte[]类型，
            xmlString = new String(strBuffer);
            Document doc = Jsoup.parse(xmlString);
            return doc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document getDocumentByFileUtils(String path){
        try {

            String str =FileUtils.readFileToString(new File(path),"UTF8");
            Document doc = Jsoup.parse(str);
            return doc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
