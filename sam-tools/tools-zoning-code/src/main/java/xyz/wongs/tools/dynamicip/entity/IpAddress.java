package xyz.wongs.tools.dynamicip.entity;

import xyz.wongs.basic.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-cloud xyz.wongs.tools.dynamicip.entity
 * @Description: TODO
 * @date 2018/7/2 23:25
 **/
@Entity
@Table(name="TB_IPADDRESS")
public class IpAddress extends AbstractEntity<Long> {

    public IpAddress(){
        super();
    }

    public IpAddress(String ip, Integer port, String type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    public IpAddress(String ip, Integer port, String anonymity, String type, String nation, String province, String networkOperators, String respondingSpeed, String finalVerificationTime) {
        this.ip = ip;
        this.port = port;
        this.anonymity = anonymity;
        this.type = type;
        this.nation = nation;
        this.province = province;
        this.networkOperators = networkOperators;
        this.respondingSpeed = respondingSpeed;
        this.finalVerificationTime = finalVerificationTime;
        this.cDate=new Date();
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

    @Column(name="ip",length=27,nullable = false)
    private String ip;

    @Column(name="port",length=6,nullable = false)
    private Integer port;

    /**
     * 匿名度,透明 | 高匿
     */
    @Column(name="anonymity",length=10)
    private String anonymity;

    /**
     * 类型 HTTP |HTTPS
     */
    @Column(name="type",length=5)
    private String type;
    /**
     * 国家
     */
    @Column(name="nation",length=120)
    private String nation;

    @Column(name="province",length=120)
    private String province;

    /**
     * 运营商
     */
    @Column(name="network_operators",length=120)
    private String networkOperators;

    /**
     * 响应速度
     */
    @Column(name="responding_speed",length=14)
    private String respondingSpeed;

    /**
     * 创建时间
     */
    @Column(name="c_date")
    private Date cDate;

    /**
     * 权重
     */
    @Column(name="weights")
    private Integer weights;

    /**
     * 状态
     */
    @Column(name="status")
    private String status;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 验证时间
     */
    @Column(name="final_verification_time",length=16)
    private String finalVerificationTime;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getNetworkOperators() {
        return networkOperators;
    }

    public void setNetworkOperators(String networkOperators) {
        this.networkOperators = networkOperators;
    }

    public String getRespondingSpeed() {
        return respondingSpeed;
    }

    public void setRespondingSpeed(String respondingSpeed) {
        this.respondingSpeed = respondingSpeed;
    }

    public String getFinalVerificationTime() {
        return finalVerificationTime;
    }

    public void setFinalVerificationTime(String finalVerificationTime) {
        this.finalVerificationTime = finalVerificationTime;
    }

    public Date getcDate() {

        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Integer getWeights() {
        return weights;
    }

    public void setWeights(Integer weights) {
        this.weights = weights;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", anonymity='" + anonymity + '\'' +
                ", type='" + type + '\'' +
                ", nation='" + nation + '\'' +
                ", province='" + province + '\'' +
                ", networkOperators='" + networkOperators + '\'' +
                ", respondingSpeed='" + respondingSpeed + '\'' +
                ", cDate=" + cDate +
                ", weights=" + weights +
                ", status='" + status + '\'' +
                ", finalVerificationTime='" + finalVerificationTime + '\'' +
                '}';
    }
}
