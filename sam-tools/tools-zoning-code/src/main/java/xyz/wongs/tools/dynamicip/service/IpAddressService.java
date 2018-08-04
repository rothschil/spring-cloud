package xyz.wongs.tools.dynamicip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.basic.common.exception.NullService;
import xyz.wongs.basic.common.service.BaseService;
import xyz.wongs.tools.dynamicip.entity.IpAddress;
import xyz.wongs.tools.dynamicip.repository.IpAddressRepository;
import xyz.wongs.tools.utils.MethodUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip.service
 * @Description: TODO
 * @date 2018/7/3 9:06
 **/
@Service("ipAddressService")
@Transactional(readOnly = true,rollbackFor = NullService.class)
public class IpAddressService extends BaseService<IpAddress, Long> {

    private final static Logger logger = LoggerFactory.getLogger(IpAddressService.class);

    private IpAddressRepository ipAddressRepository;

    public IpAddressService() {
        super();
    }

    @Autowired
    @Qualifier("ipAddressRepository")
    @Override
    public void setJpaRepository(JpaRepository<IpAddress, Long> jpaRepository) {
        // TODO Auto-generated method stub
        this.jpaRepository=jpaRepository;
        this.ipAddressRepository=(IpAddressRepository)jpaRepository;
    }



//    @Transactional(rollbackFor = Exception.class)
    @Override
    @Transactional
    public IpAddress save(IpAddress ipAddress) {
        return super.save(ipAddress);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public IpAddress saveAndFlush(IpAddress ipAddress) {
        return super.saveAndFlush(ipAddress);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(IpAddress ipAddress) {
        super.delete(ipAddress.getId());
    }

    public List<IpAddress> findIpAddressByType(String type){
          return ipAddressRepository.findByType(type);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delAddress(IpAddress ipAddress){
        ipAddressRepository.delAddress("del",ipAddress.getId());
    }




    @Transactional(rollbackFor = Exception.class)
    public void updateWeights(IpAddress ipAddress){
        int weights = ipAddress.getWeights()+1;
        System.out.println(ipAddress.toString()+" weights ="+weights);
        ipAddressRepository.updateWeights(weights,ipAddress.getId());
    }

    public int getIpAddressCounts(String type){
        //每页大小
        int pageSize=10;
        int size = ipAddressRepository.getIpAddressCounts(type).intValue();
        if(size%pageSize!=0) {
            return  (size/pageSize)+1;
        } else {
            return  size/pageSize;
        }
    }


    public Page<IpAddress> findEntityCriteria(Integer page, Integer size, IpAddress ipAddress) {
        // TODO Auto-generated method stub
        Pageable pageable = new PageRequest(page, size);
        try {
            return ipAddressRepository.findAll(new Specification<IpAddress>(){
                @Override
                public Predicate toPredicate(Root<IpAddress> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> lp = new ArrayList<Predicate>();
                    MethodUtil.getFieldValue(ipAddress,root,cb,lp);
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


    public List<IpAddress> findByNation(String nation){
         return ipAddressRepository.findByNation(nation);
    }

    public Page<IpAddress> findIpAddress(int pageNumber,String type,Sort sort) {
        //每页大小
        int pageSize=5;
        //默认排序
        if(null==sort){
            sort = new Sort(Sort.Direction.DESC, "id");
        }

        Pageable pageable = new PageRequest(pageNumber,pageSize,sort);
        return ipAddressRepository.getIpAddress(type,pageable);
    }

//    @Override
//    public Page<IpAddress> findEntityCriteria(Integer page, Integer size, IpAddress ipAddress) {
//        return null;
//    }

//    @Override
//    public Page<IpAddress> findEntityCriteria(Integer page, Integer size, IpAddress ipAddress) {
//        // TODO Auto-generated method stub
//        Pageable pageable = new PageRequest(page, size);
//        Page<IpAddress> lePage = null;
//        try {
//            lePage = ipAddressRepository.findAll(new Specification<IpAddress>(){
//                @Override
//                public Predicate toPredicate(Root<IpAddress> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                    List<Predicate> lp = new ArrayList<Predicate>();
//                    int length = getPredicate(ipAddress,root,cb,lp);
//                    Predicate[] pe = new Predicate[length];
//                    query.where(cb.and(lp.toArray(pe)));
//                    return query.getRestriction();
//                }
//
//            }, pageable);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return lePage;
//    }


}
