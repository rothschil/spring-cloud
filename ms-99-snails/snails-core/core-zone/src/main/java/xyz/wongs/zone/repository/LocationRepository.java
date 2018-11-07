package xyz.wongs.zone.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.wongs.common.abs.repository.BaseRepository;
import xyz.wongs.zone.entity.Location;

import java.util.List;

public interface LocationRepository extends BaseRepository<Location, Long>,JpaSpecificationExecutor<Location> {

	List<Location> getLocationBySupLocalCode(@Param("supLocalCode") String supLocalCode);

	@Query("from Location t where t.lv = :lv and t.flag is null")
	List<Location> getLocationByLvAndFlagIsNull(@Param("lv") int lv);

	@Modifying(clearAutomatically = true)
	@Query("update from Location t set t.flag=:flag where t.id=:id")
	void updateLocationFlag(@Param("flag") String flag, @Param("id") Long id);

	@Query(value="from Location t where t.lv = :lv ")
	List<Location> getLocationListByLevel(@Param("lv") Integer lv);

	@Query(value="from Location t where t.id = :id")
	List<Location> getLocationListById(@Param("id") Long id);

	@Query(value="select t.* from tb_location t "+
			" start with t.local_code =?1 connect by  t.local_code = prior t.sup_local_code",nativeQuery = true)
	List<Location> findLocationTreeNodeByLocalCode(String localCode);


	@Query(value="select t.* " +
			" from tb_location t where t.lv=?1 and t.flag is null and rownum<?2",nativeQuery = true)
	List<Location> findLocationThrid(Integer lv, Integer rownum);

	@Query(value = " FROM Location b where b.url is not null and b.flag is null and b.lv=:lv")
	Page<Location> getLocationsByLevel(@Param("lv") Integer lv, Pageable pageable);

	@Query(value="select count(*) from Location t where t.flag is null AND t.lv = :lv and t.url is not null")
	Long getLocationCountsByLevel(@Param("lv") Integer lv);
}
