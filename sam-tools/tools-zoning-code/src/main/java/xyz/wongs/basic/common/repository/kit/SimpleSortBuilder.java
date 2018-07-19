package xyz.wongs.basic.common.repository.kit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 *
 * <p>Title:SimpleSortBuilder </p>
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
 * @date:   2017年8月5日 下午4:02:18  *
 * @since JDK 1.7
 */
public class SimpleSortBuilder {

	/**
	 *
	 * @Title: generateSort
	 * @Description: 调用的时候使用SimpleSortBuilder.generateSort("name","xh_d");表示先以name升序，之后以xh降序
	 * @param fields
	 * @return  Sort
	 */
	public static Sort generateSort(String... fields) {
		List<Order> orders = new ArrayList<Order>();
		for(String f:fields) {
			orders.add(generateOrder(f));
		}
		return new Sort(orders);
	}

	/**
	 *
	 * @Title: generateOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param f
	 * @return  Order
	 */
	private static Order generateOrder(String f) {
		Order order = null;
		String[] ff = f.split("_");
		if(ff.length>=2) {
			if(ff[1].equals("d")) {
				order = new Order(Direction.DESC,ff[0]);
			} else {
				order = new Order(Direction.ASC,ff[0]);
			}
			return order;
		}
		order = new Order(f);
		return order;
	}
}
