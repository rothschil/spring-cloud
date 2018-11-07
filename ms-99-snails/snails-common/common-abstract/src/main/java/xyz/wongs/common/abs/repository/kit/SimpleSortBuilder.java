package xyz.wongs.common.abs.repository.kit;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

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
 * @ClassName SimpleSortBuilder
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/28 17:13
 * @Version 1.0.0
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
