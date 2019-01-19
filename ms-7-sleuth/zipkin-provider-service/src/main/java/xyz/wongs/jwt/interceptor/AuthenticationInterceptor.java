package xyz.wongs.jwt.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.service.TokenEndpoint;
import xyz.wongs.domain.User;
import xyz.wongs.domain.UserRepository;
import xyz.wongs.exception.NoUserException;
import xyz.wongs.jwt.interf.PassToken;
import xyz.wongs.jwt.interf.UserLoginToken;
import xyz.wongs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

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
 * @ClassName AuthenticationInterceptor
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/17 20:29
 * @Version 1.0.0
*/
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static String TOEKN = "token";

    @Autowired
    UserRepository userRepository;

    /**预处理回调方法,实现处理器的预处理，第三个参数为响应的处理器,自定义Controller,返回值为true表示继续流程（如调用下一个拦截器或处理器）
     * 或者接着执行postHandle()和afterCompletion()；false表示流程中断，不会继续调用其他的拦截器或处理器，中断执行。
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/1/17 20:56
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @throws
     * @since
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(TOEKN);
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                if(null == token){
                    throw new RuntimeException("无token，请重新登录");
                }
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                    User user = userRepository.getOne(Long.valueOf(userId));

                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getName())).build();

                    jwtVerifier.verify(token);
                    return true;
                } catch (JWTDecodeException e) {
                    throw new RuntimeException("401");
                } catch (NoUserException e) {
                    throw new NoUserException("用户不存在，请重新登录");
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
            }
        }
        return false;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（DispatcherServlet进行视图返回渲染之前进行调用），
     * 此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/1/17 20:55
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @return void
     * @throws
     * @since
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**整个请求处理完毕回调方法,该方法也是需要当前对应的Interceptor的preHandle()的返回值为true时才会执行，
     * 也就是在DispatcherServlet渲染了对应的视图之后执行。用于进行资源清理。整个请求处理完毕回调方法。
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/1/17 20:56
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return void
     * @throws
     * @since
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }
}
