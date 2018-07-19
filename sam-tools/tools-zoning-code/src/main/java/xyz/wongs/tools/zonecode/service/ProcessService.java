package xyz.wongs.tools.zonecode.service;

import xyz.wongs.tools.zonecode.entity.Location;

import java.util.List;


public interface ProcessService {


	/**
	 *
	 * @Title: htmlParser
	 * @Description: 解析HTML
	 * @return: void
	 */
	void htmlParser();

	void getHTML2(String url,Location location);
	/**
	 *
	 * @Title: htmlParser
	 * @Description: 解析HTML
	 * @return: void
	 */
	void getHTML(String url,Location location);

	void thridLevelResolve(String url,Location location);
	void thridLevelResolve(String url,Location location,String flag);
	/**
	 *
	 * @Title: getLocationToFirstLevel
	 * @Description: 获取第一级地市信息  ，安徽默认是16个地市
	 * @param url	解析的URL
	 * @param rootCode	父级节点
	 * @param flag  true:从URL中获取，false:从数据库中获取，默认为false
	 * @return List<Location>
	 */
	List<Location> getLocationToFirstLevel(String url, String rootCode, Boolean flag);

	/**
	 *
	 * @Title: getLocationOneLevel
	 * @Description:
	 * @param url
	 * @param firstlevelSupLocalCode
	 * @return: List<Location>
	 */
	void getLocationToSecondLevel(String url, String firstlevelSupLocalCode);

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
	void intiRootUrl(String url);

	/**
	 *
	 * @Title: getLocationThirdLevel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param url
	 * @param secondLevelLocas List<Location>
	 * @return: void
	 */
	void getLocationToThirdLevel(String url, List<Location> secondLevelLocas);

	/**
	 *
	 * @Title: getLocationFourthLevel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param url
	 * @param thridLevelLocas
	 * @return: void
	 */
	void getLocationToFourthLevel(String url, List<Location> thridLevelLocas);

	void getLocationThrid();
}
