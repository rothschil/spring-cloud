package xyz.wongs.tools.zonecode.entity;

import xyz.wongs.basic.common.entity.AbstractEntity;

import javax.persistence.*;


@Entity
@Table(name="TB_LOCATIONS")
public class Location extends AbstractEntity<Long> {

	/**
	 * @SequenceGenerator(name="seq",sequenceName="SEQ_LOCATION_ID")
	 * public class Location extends BaseOracleEntity<Long> {
	 */
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1303783997218810176L;

	public Location() {
		super();
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id")
	private Long id;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long aLong) {
	   this.id=aLong;
	}



	public Location(String localCode, String localName, String supLocalCode, String url, Integer lv) {
		super();
		this.localCode = localCode;
		this.localName = localName;
		this.supLocalCode = supLocalCode;
		this.url = url;
		this.lv=lv;
	}

	public Location(String localCode, String localName, String supLocalCode, String url, Integer lv,String flag) {
		super();
		this.localCode = localCode;
		this.localName = localName;
		this.supLocalCode = supLocalCode;
		this.url = url;
		this.lv=lv;
		this.flag=flag;
	}

	@Column(name="local_code",length=15,nullable = false)
    private String localCode;

    @Column(name="local_name",length=20,nullable = false)
    private String localName;

    @Column(name="sup_local_code",length=15,nullable = false)
    private String supLocalCode;

    @Column(name="url",length=20,nullable = true)
    private String url;

    @Column(name="lv",length=1,nullable = true)
    private Integer lv;

    @Column(name="flag",length=2,nullable = true)
    private String flag;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getSupLocalCode() {
		return supLocalCode;
	}

	public void setSupLocalCode(String supLocalCode) {
		this.supLocalCode = supLocalCode;
	}



	public Integer getLv() {
		return lv;
	}



	public void setLv(Integer lv) {
		this.lv = lv;
	}



	public String getFlag() {
		return flag;
	}



	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "localCode=>"+localCode+" ;localName=>"+localName+" ;supLocalCode=>"+supLocalCode+" ;url=>"+url;
	}
}
