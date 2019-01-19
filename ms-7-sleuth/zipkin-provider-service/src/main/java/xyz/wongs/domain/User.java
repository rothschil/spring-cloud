package xyz.wongs.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.hibernate.annotations.Proxy;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.domain
 * @Description: TODO
 * @date 2018/6/20 16:35
 **/
//@Proxy(lazy = false)
@Entity
@Table(name ="TB_USER")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer age;

    public User(){}

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
