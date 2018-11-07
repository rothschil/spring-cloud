package xyz.wongs.zone.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.common.abs.entity.AbstractEntity;
import xyz.wongs.common.abs.exception.NullService;
import xyz.wongs.common.abs.service.BaseService;
import xyz.wongs.zone.entity.Location;
import xyz.wongs.zone.repository.LocationRepository;
import xyz.wongs.zone.utils.MethodUtil;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@Service(value="locationService")
@Transactional(readOnly = true,rollbackFor = NullService.class)
public class LocationService extends BaseService<Location, Long> {

	private LocationRepository locationRepository;

	public List<Location> getLocationBySupLocalCode(String supLocalCode) {
		// TODO Auto-generated method stub
		return locationRepository.getLocationBySupLocalCode(supLocalCode);
	}

	public List<Location> getLocationByLvAndFlag(int lv,String flag){
		return locationRepository.getLocationByLvAndFlagIsNull(lv);
	}

	public List<Location> findLocationTreeNodeByLocalCode(String localCode){
		return locationRepository.findLocationTreeNodeByLocalCode(localCode);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void save(List<Location> locas) {
		for(Location le:locas){
			locationRepository.save(le);
		}
	}

	public List<Location> getLocationListByLevel(Integer lv){
		return locationRepository.getLocationListByLevel(lv);
	}

	public List<Location> getLocationListById(Long id){
		return locationRepository.getLocationListById(id);
	}

	public Page<Location> getLocationsByLevel(Integer lv,int pageNumber){
		//每页大小
		int pageSize=20;
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(pageNumber,pageSize,sort);
		return locationRepository.getLocationsByLevel(lv,pageable);
	}

	public int getLocationCountsByLevel(Integer lv){
		//每页大小
		int pageSize=20;
		int size = locationRepository.getLocationCountsByLevel(lv).intValue();
		if(size%pageSize!=0) {
		   return  (size/pageSize)+1;
		} else {
			return  size/pageSize;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void updateLocationFlag(String flag, Long id) {
		locationRepository.updateLocationFlag(flag, id);
	}

	/**
	 * 分页查询
	 * @param page
	 * @param size
	 * @return
	 * @see xyz.wongs.common.abs.service.BaseService#findEntityNoCriteria(Integer, Integer)
	 */
	@Override
	public Page<Location> findEntityNoCriteria(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return super.findEntityNoCriteria(page, size);
	}

	public List<Location> findLocationThrid(Integer lv, Integer rownum) {
		// TODO Auto-generated method stub
		return locationRepository.findLocationThrid(lv, rownum);
	}



	public Page<Location> findEntityCriteria(Integer page, Integer size, Location location) {
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page, size);
		try {
			return locationRepository.findAll(new Specification<Location>(){
				@Override
				public Predicate toPredicate(Root<Location> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					// TODO Auto-generated method stub
					List<Predicate> lp = new ArrayList<Predicate>();
					MethodUtil.getFieldValue(location,root,cb,lp);
					Predicate[] pe = new Predicate[lp.size()];
					query.where(cb.and(lp.toArray(pe)));
					return query.getRestriction();
				}

			}, pageable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public int getPredicate(AbstractEntity<?> ae, Root<?> root, CriteriaBuilder cb, List<Predicate> lp){
		Field[] fd = ae.getClass().getFields();
		Method[] method = ae.getClass().getDeclaredMethods();

		for(Method m:method){
			if(m.getName().startsWith("get")){
				String mm = m.getName().substring(3);
				mm = mm.substring(0, 1).toLowerCase()+mm.substring(1);
				for(Field f:fd){
					if(f.getName().equals(mm)){
						try {
							lp.add(cb.equal(root.get(mm).as(f.getType()),m.invoke(ae)));
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
//		Predicate[] pe = new Predicate[method.length];
		return method.length;
	}


	@Autowired
	@Qualifier("locationRepository")
	@Override
	public void setJpaRepository(JpaRepository<Location, Long> jpaRepository) {
		// TODO Auto-generated method stub
		this.jpaRepository=jpaRepository;
		this.locationRepository=(LocationRepository)jpaRepository;
	}

}
