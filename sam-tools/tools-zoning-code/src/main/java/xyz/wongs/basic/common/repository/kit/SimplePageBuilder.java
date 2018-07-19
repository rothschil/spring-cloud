package xyz.wongs.basic.common.repository.kit;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * <p>Title:SimplePageBuilder </p>
 * <p>@Description: </p>
 * <p>Company: </p>
 * ----------------------------------------
 *	\\	 /\/\   /\|	|/\   /\/\	 //
 *	 \\^^  ^^^  ^^|_|^^  ^^^  ^^//
 *	  \\^   ^^^  ^/Ϡ\^   ^^^  ^//
 *	   \\^ ^    ^/___\^    ^ ^//
 *	    \\^ ^^ ^//   \\^ ^^ ^//
 *	     \\	^^/(/     \)\^^ //
 *	      \\^'//       \\'^//
 *	       .==.   खान          .==.
 * ----------------------------------------
 * @author: <a href="wcngs@qq.com">WCNGS</a>
 * @date:   2017年8月5日 下午4:20:02  *
 * @since JDK 1.7
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
