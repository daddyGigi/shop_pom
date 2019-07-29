package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * Created by  .Life on 2019/07/21/0021 13:23
 */
@Aspect
//切面类要让spring扫到，必须加这个注解
@Component
public class LoginAop {
    @Autowired
    private RedisTemplate redisTemplate;

    //切点表达式--告诉spring这个方法是去环绕加了这个注解的方法
    @Around("@annotation(IsLogin)")
    public Object handlLogin(ProceedingJoinPoint proceedingJoinPoint){
        
        //获得request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获得Cookie
        String loginToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginToken")){
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }
        //通过凭证去redis中获取用户信息
        User loginUser = null;
        if (loginToken != null){
            loginUser = (User) redisTemplate.opsForValue().get(loginToken);
        }
        //判断是否已经登录
        if (loginUser == null){
            //当期未登录，判断注解IsLogin中的mustLogin是否为true，如果为true强制跳转到登录页面，如果为false则不作处理

            //获得增强的目标签名
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //获得目标方法的Method对象
            Method method = signature.getMethod();
            //获得方法上的注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);

            //判断是否要强制登录
            if (isLogin.mustLogin()){
                //说明要强制登录，获得当前请求的url
               String url = request.getRequestURL().toString();
                try {
                    url = URLEncoder.encode(url,"utf-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "redirect:http://localhost:8084/sso/tologin?returnUrl="+url;
            }
        }
        //已经登录，或者不需要登录
        //修改目标方法的实参列表
        //获得目标方法的实际参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i].getClass() == User.class){
                //覆盖原来的参数值
                  args[i] = loginUser;
                  break;
            }
        }
        //最后，用我的实参列表调用目标方法
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;

    }
}
