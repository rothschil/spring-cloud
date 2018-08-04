package xyz.wongs.tools.dynamicip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.wongs.basic.common.repository.BaseRepository;
import xyz.wongs.tools.dynamicip.entity.IpAddress;

import java.util.List;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip.repository
 * @Description: TODO
 * @date 2018/7/3 9:03
 **/
public interface IpAddressRepository extends BaseRepository<IpAddress, Long>,JpaSpecificationExecutor<IpAddress> {

    @Query(value="select count(*) from IpAddress t where t.status is null AND t.type = :type ")
    Long getIpAddressCounts(@Param("type") String type);


    @Query(value="from IpAddress t where t.status is null AND t.type = :type ")
    Page<IpAddress> getIpAddress(@Param("type") String type, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value="update from IpAddress set status=:status where id = :id ")
    void delAddress(@Param("status") String status, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query(value="update from IpAddress set weights=:weights where id = :id ")
    void updateWeights(@Param("weights") int weights, @Param("id") Long id);

    List<IpAddress> findByType(@Param("type") String type);

    @Query(value="from IpAddress t where t.status is null AND t.type = :type ")
    Page<IpAddress> findByType(@Param("type") String type, Pageable pageable);

    List<IpAddress> findByStatusIsNull(@Param("status") String status);

    List<IpAddress> findByNation(@Param("nation") String nation);
}
