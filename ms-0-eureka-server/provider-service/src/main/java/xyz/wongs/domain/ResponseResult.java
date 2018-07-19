package xyz.wongs.domain;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:   返回值实体类
 * @Package spring-boot xyz.wongs.domain
 * @Description: TODO
 * @date 2018/6/21 14:43
 **/
public class ResponseResult {
    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
