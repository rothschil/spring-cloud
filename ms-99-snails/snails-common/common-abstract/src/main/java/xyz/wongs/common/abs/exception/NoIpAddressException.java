package xyz.wongs.common.abs.exception;

import java.io.PrintWriter;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName NoIpAddressException
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/28 17:13
 * @Version 1.0.0
*/
public class NoIpAddressException extends Exception {

    public NoIpAddressException() {
        super();
    }

    public NoIpAddressException(String message) {
        super(message);
    }
    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        // TODO Auto-generated method stub
        super.printStackTrace(s);
    }
}
