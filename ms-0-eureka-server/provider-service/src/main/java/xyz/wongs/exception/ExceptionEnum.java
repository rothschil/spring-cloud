package xyz.wongs.exception;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.exception
 * @Description: TODO
 * @date 2018/6/21 14:36
 **/
public enum ExceptionEnum {
    ERROR_NOFOUND("无法找到相应的数据");

    private String value;

    public String getValue() {
        return value;
    }

    ExceptionEnum(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
