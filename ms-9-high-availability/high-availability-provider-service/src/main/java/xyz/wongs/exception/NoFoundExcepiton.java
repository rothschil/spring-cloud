package xyz.wongs.exception;

/**
 * @author WCNGS@QQ.CO
 * @version V1.0
 * @Title:
 * @Package spring-boot xyz.wongs.exception
 * @Description: TODO
 * @date 2018/6/21 14:40
 **/
public class NoFoundExcepiton extends Exception {

    private static final long serialVersionUID = -5955607821816077172L;

    public NoFoundExcepiton(String errorInfo) {
        super(errorInfo);
    }
}
