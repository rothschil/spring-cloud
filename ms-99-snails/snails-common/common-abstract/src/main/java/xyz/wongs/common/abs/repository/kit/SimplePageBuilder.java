package xyz.wongs.common.abs.repository.kit;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
 * @ClassName SimplePageBuilder
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/28 17:13
 * @Version 1.0.0
*/
public class SimplePageBuilder {

	/**
	 * 以常量的形式存储，在实际的运用中应该从properties文件中取得，思路都一样
	 */
	public static final int size = 15;

	/**
	 *
	 * @Title: generate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param size
	 * @param sort
	 * @return  Pageable
	 */
	public static Pageable generate(int page,int size,Sort sort) {
		if(sort==null) return new PageRequest(page, size);
		return new PageRequest(page, size, sort);
	}

	/**
	 *
	 * @Title: generate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @return  Pageable
	 */
	public static Pageable generate(int page) {
		return generate(page,size,null);
	}

	/**
	 *
	 * @Title: generate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param sort
	 * @return  Pageable
	 */
	public static Pageable generate(int page,Sort sort) {
		return generate(page,size,sort);
	}
}
