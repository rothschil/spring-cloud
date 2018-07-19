package xyz.wongs.tools.zonecode.service.task;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.wongs.basic.common.util.FileTools;
import xyz.wongs.tools.zonecode.entity.Location;
import xyz.wongs.tools.zonecode.service.LocationService;
import xyz.wongs.tools.zonecode.service.ProcessService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @ClassName:  JsoupProcessServiceImpl
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: <a href="wcngs@qq.com">WCNGS</a>
 * @date:   2017年7月28日 上午11:31:30  *
 * @Copyright: 2017 WCNGS Inc. All rights reserved.
 */
@Service("processService")
public class ProcessServiceImpl implements ProcessService {

	private final static Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

	@Autowired
	@Qualifier("locationService")
	LocationService locationService;

	@Override
	public void htmlParser(){
		try {
			String path = FileTools.getCurtFilePath(true)+"/out/2017-07-28-14-40-19.html";
			Document doc = Jsoup.parse(new File(path), "UTF-8", "http://www.dangdang.com");
			Elements elems = doc.getElementsByClass("MsoNormal");
			for (Element el : elems) {
				elems = el.getElementsByAttribute("lang");
				System.out.print(elems.get(0).text()+"\t");

				elems = el.getElementsByAttribute("style");
				int i=0;
				switch(elems.size()){
					case 0: continue;
					case 1:i=0 ;break;
					case 2:i=1;break;
					default :
				}

				System.out.println(elems.get(i).text().trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void getHTML(String url,Location location) {
		List<Location> firstLevelLocas = getLocationOneLevel(url,location.getLocalCode());
		save(firstLevelLocas);
	}

	@Override
	public void getHTML2(String url,Location location) {
		List<Location> secondLevelLocas =getLocationSecondLevel(url,location);
		save(secondLevelLocas);
	}

	/**
	 * 初始化省、直辖区、自治区
	 * @method      intiRootUrl
	 * @author      WCNGS@QQ.COM
	 * @version
	 * @see
	 * @param url
	 * @return      void
	 * @exception
	 * @date        2018/6/30 23:29
	 */
	@Override
	public void intiRootUrl(String url){

		List<Location> rootLocations = getLocationRoot(url,"0");
		save(rootLocations);
	}

	public List<Location> getLocationRoot(String url,String pCode){

		List<Location> locas = new ArrayList<Location>(27);
		Elements eles = getElementsByConnection(url,"provincetr");
		for(Element e:eles){
			Elements el2 = e.getElementsByTag("td").select("a[href]");
			if(null==el2 || el2.size()==0){
				continue;
			}
			for(Element et:el2) {
				String urls = et.attributes().asList().get(0).getValue();
				locas.add(new Location(urls.substring(0, 2), et.text(), "0", urls, 0));
			}
		}
		return locas;
	}

	/**
	 * 方法实现说明
	 * @method      thridLevelResolve
	 * @author      WCNGS@QQ.COM
	 * @version
	 * @see
	 * @param url
	 * @param location
	 * @return      void
	 * @exception
	 * @date        2018/7/1 9:50
	 */
	@Override
	public void thridLevelResolve(String url,Location location){

		this.thridLevelResolve(url,location,"Y");
	}


	/**
	 * 方法实现说明
	 * @method      thridLevelResolve
	 * @author      WCNGS@QQ.COM
	 * @version
	 * @see
	 * @param url
	 * @param location
	 * @param flag
	 * @return      void
	 * @exception
	 * @date        2018/7/1 16:24
	 */
	@Override
	public void thridLevelResolve(String url,Location location,String flag){

		try {
			if(StringUtils.isEmpty(location.getUrl())){
				return;
			}
			List<Location> thridLevelLocas =getLocation(url,new String[]{"towntr","href"},location.getLocalCode(),3,flag);
			locationService.updateLocationFlag(flag,location.getId());
			save(thridLevelLocas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(List<Location> locas){
		//结果为空，抛出异常
		if(null==locas){
			logger.error(" target saved is null!");
			throw new NullPointerException(" List Location is null！");
		}

		locationService.save(locas);

	}

	public  void getLocationThirdLevel(String url,List<Location> secondLevelLocas){

		for(Location le:secondLevelLocas){
			if(StringUtils.isEmpty(le.getUrl())){
				continue;
			}
			//Elements es = getElementsByConnection(prefix+le.getUrl(),"towntr");
			List<Location> thridLevelLocas =getLocation(url,new String[]{"towntr","href"},le.getLocalCode(),3);
//			save(thridLevelLocas);
			locationService.save(thridLevelLocas);

//			getLocationFourthLevel(prefix+le.getUrl(),thridLevelLocas);
		}

	}

	public  void getLocationFourthLevel(String url,List<Location> thridLevelLocas){
		//http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/34/01/340122.html
		String prefix = xyz.wongs.tools.zonecode.ZoneCodeStringUtils.interceptionStringByLastIndexOf(url,"/")+"/";
		//
		List<Location> locas = new ArrayList<Location>(20);
		for(Location le:thridLevelLocas){
			Elements es = getElementsByConnection(prefix+le.getUrl(),"villagetr");
			for(Element e:es){
				locas.add(new Location(e.child(0).text(),e.child(2).text(),le.getLocalCode(),null,4));
			}
		}

		locationService.save(locas);


	}


	/**
	 *
	 * @Title: getLocationSecondLevel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param url
	 * @param location
	 * @return
	 * @return: List<Location>
	 */
	public List<Location> getLocationSecondLevel(String url,Location location){
		List<Location> locas = null;
		try {
			locas = new ArrayList<Location>(90);
			//URL地址截取
			//标识位
			boolean flag =false;
			Elements es = getElementsByConnection(url,"countytr");
			for(Element e:es){
                //针对市辖区 这种无URL的做特殊处理
                if(!flag){
                    locas.add(new Location(e.child(0).text(),e.child(1).text(),location.getLocalCode(),null,2));
                    //标识位置为TURE
                    flag=true;
                    continue;
                }
				es = e.getElementsByAttribute("href");
				if(es.size()==0){
					locas.add(new Location(e.child(0).text(),e.child(1).text(),location.getLocalCode(),"",2));
					continue;
				}
				List<Attribute> attrs = es.get(0).attributes().asList();
				locas.add(new Location(es.get(0).text(),es.get(1).text(),location.getLocalCode(),attrs.get(0).getValue(),2));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locas;
	}



	/**
	 *
	 * @Title: getLocationOneLevel
	 * @Description:
	 * 1、获取第一级地市信息  ，安徽默认是16个地市
	 * 2、第二级区县信息
	 * @param url
	 * @param pCode
	 * @return
	 * @return: List<Location>
	 */
	public List<Location> getLocationOneLevel(String url,String pCode){

		List<Location> locas = new ArrayList<Location>(20);
		Elements eles = getElementsByConnection(url,"citytr");
		for(Element e:eles){
			eles = e.getElementsByAttribute("href");
			List<Attribute> attrs = eles.get(0).attributes().asList();
			locas.add(new Location(eles.get(0).text(),eles.get(1).text(),pCode,attrs.get(0).getValue(),1));
		}
		return locas;
	}


	public List<Location> getLocation(String url,String[] cssClazz,String parentURLCode,Integer lv,String flag){
		List<Location> locas = new ArrayList<Location>(20);
		Elements eles = getElementsByConnection(url,cssClazz[0]);
		for(Element e:eles){
			eles = e.getElementsByAttribute(cssClazz[1]);
			List<Attribute> attrs = eles.get(0).attributes().asList();
			locas.add(new Location(eles.get(0).text(),eles.get(1).text(),parentURLCode,attrs.get(0).getValue(),lv,flag));
		}
		return locas;
	}
	/**
	 * 案例
	 * <tr class='towntr'>
	 * 	<td><a href='02/340102001.html'>340102001000</a></td>
	 * 	<td><a href='02/340102001.html'>明光路街道</a></td>
	 * </tr>
	 * @Title: getLocation
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param url
	 * @param cssClazz
	 * @param parentURLCode
	 * @return  List<Location>
	 */
	public List<Location> getLocation(String url,String[] cssClazz,String parentURLCode,Integer lv){
		 return getLocation(url,cssClazz,parentURLCode,lv);
	}


	/**
	 * 方法实现说明
	 * @method      getElementss
	 * @author      WCNGS@QQ.COM
	 * @version
	 * @see
	 * @param url
	 * @param clazzName
	 * @return      org.jsoup.select.Elements
	 * @exception
	 * @date        2018/7/2 11:28
	 */
	public Elements getElementsByConnection(String url,String clazzName){

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

			RequestConfig config = RequestConfig.custom()
					//.setProxy(proxy)
					//设置连接超时 ✔
					// 设置连接超时时间 10秒钟
					.setConnectTimeout(10000)
					// 设置读取超时时间10秒钟
					.setSocketTimeout(10000)
					.build();
			httpget.setConfig(config);
			// 执行get请求
			CloseableHttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// 获取返回实体
			String content = EntityUtils.toString(entity, "GBK");
			// ============================= 【Jsoup】 ====================================
			//获取响应类型、内容
//			logger.error("Status:"+response.getStatusLine().getStatusCode());
//			logger.error("Content-Type:"+entity.getContentType().getValue());

//			Connection connection = HttpConnection.connect(new URL(url)).timeout(15000);
//			Document doc = connection.get();
			Document doc = Jsoup.parse(content);
			return doc.getElementsByClass(clazzName);
		} catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			logger.error(" URL: "+url+",clazzName:"+clazzName);
		} catch (IOException e) {
			logger.error(" URL: "+url+",clazzName:"+clazzName);
		}
		return null;
	}

//	/**
//	 *
//	 * @Title: getElementsByConnection
//	 * @Description: 获取Elements
//	 * @param url
//	 * @param clazzName
//	 * @return
//	 * @return: Elements
//	 */
////	public Elements getElementsByConnection(String url,String clazzName){
////		try {
////			Connection connection = HttpConnection.connect(new URL(url)).timeout(15000);
////			Document doc = connection.get();
////			return doc.getElementsByClass(clazzName);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			logger.error(" URL: "+url+",clazzName:"+clazzName);
////			throw new RuntimeException(e);
////		}
////	}


	@Override
	public List<Location> getLocationToFirstLevel(String url,String firstlevelSupLocalCode,Boolean flag) {
		// TODO Auto-generated method stub
		flag =flag==null?false:flag;
		List<Location> loca = null;
		if(!flag){
			loca = locationService.getLocationBySupLocalCode(firstlevelSupLocalCode);
		} else {
			loca = getLocationOneLevel(url,firstlevelSupLocalCode);
			//如果不是从数据库中可以保存
//			save(loca);
		}
		return loca;
	}


	@Override
	public void getLocationToSecondLevel(String url,String rootCode) {
		// TODO Auto-generated method stub
//		List<Location> loca = getLocationSecondLevel(url,getLocationToFirstLevel(url,rootCode,false));
//		save(loca);
	}

	@Override
	public void getLocationToThirdLevel(String url, List<Location> secondLevelLocas) {

	}

	@Override
	public void getLocationToFourthLevel(String url, List<Location> thridLevelLocas) {

	}

	@Override
	public void getLocationThrid() {
		String prefix ="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";
		List<Location> ll = locationService.findLocationThrid(3,31);
		for(Location le:ll){
			List<Location> llv2 = locationService.findLocationTreeNodeByLocalCode(le.getLocalCode());
			String url= prefix+appengUrl(llv2);

			Elements eles = getElementsByConnection(url,"villagetr");
			List<Location> locas = new ArrayList<Location>(eles.size());
			for(Element e:eles){
				locas.add(new Location(e.child(0).text(),e.child(2).text(),le.getLocalCode(),null,4));
//				System.out.println("toString() => " + new Location(e.child(0).text(),e.child(2).text(),le.getLocalCode(),null,4));
			}
			locationService.updateLocationFlag("3",le.getId());
			save(locas);
			try {
				Thread.sleep(new Random().nextInt(10)*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public String appengUrl(List<Location> locations){
		Iterator<Location> it =  locations.iterator();
		String url = "";
		while(it.hasNext()){
			Location ln =it.next();
			String str= ln.getUrl();
			if (ln.getLv()==3){
				url=str;
			} else{
				int i = ln.getUrl().indexOf("/");
				url = str.substring(0, i)+"/"+url;
			}
		}
		return url;
	}

}
