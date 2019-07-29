package com.qf.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.emailcode.CodeUtil;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by  .Life on 2019/07/18/0018 18:59
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Reference
    private IUserService userService;
    @Reference
    private ICartService cartService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }


    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "ForgetPassword";
    }
    //发送验证码
    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String email){
        //设置邮件内容
        String content = "康帅傅会员注册码为：%s，如果不是本人操作，请报警";
        //验证码
        String code = CodeUtil.verifyCode();
        content = String.format(content, code);

        //将验证码放到redis中
        redisTemplate.opsForValue().set(email+"_code",code);
        //发送邮件-封装email实体类
        Email emailObj = new Email(email,"康帅傅会员注册验证码",content);
        //将邮件放入rabbitmq
        rabbitTemplate.convertAndSend("email_exchange","",emailObj);

        return "succ";
    }


    /*
    注册
     */
    @RequestMapping("/register")
    public String register(User user,String code){
        //判断验证码是否正确
        String sendCode = (String) redisTemplate.opsForValue().get(user.getEmail()+"_code");

        if (sendCode == null || !sendCode.equals(code)){
            //验证码错误
            return "redirect:/sso/toregister?error=-3";
        }
        //进行注册
        int register = userService.register(user);
        if (register > 0){
            //成功跳转到登录
            return "redirect:/sso/tologin";
        }
        return "redirect:/sso/toregister?error="+register;
    }

    //找回密码
    @RequestMapping("/sendPassword")
    @ResponseBody
    public Map<String,Object> sendPassword(String username){
        Map<String,Object> map = new HashMap<>();

        //根据用户名查询邮箱
        User user = userService.queryByUsername(username);

        if (user == null){
            //用户名不存在
            map.put("code","1000");
            return map;
        }
        //给用户发邮件
        String token = UUID.randomUUID().toString();
        //放入到redis中
        redisTemplate.opsForValue().set(username+"_token",token);
        redisTemplate.expire(username+"_token",5, TimeUnit.MINUTES);

        String url = "http://127.0.0.1:8084/sso/toChangePassword?username="+username+"&token="+token;

        Email email = new Email(user.getEmail(),"康帅傅找回密码邮件","请点击<a href='"+url+"'>找回密码</a>");

        //发送邮件
        rabbitTemplate.convertAndSend("email_exchange","",email);
        //设置通知邮箱的地址
        String emailStr = user.getEmail();
        int index = emailStr.indexOf("@");
        String emailStr2 = emailStr.replace(emailStr.substring(3,index),"*******");

        //设置去邮箱的地址
        String gomail = "mail."+ emailStr.substring(index+1);


        //代表邮件发送成功
        map.put("code","0000");
        map.put("emailStr",emailStr2);
        map.put("gomail",gomail);
        return map;
    }

    //去到修改密码的界面
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "updatepassword";
    }


    /*
    * 修改密码
    * */
    @RequestMapping("/updatepassword")
    public String updatepassword(String username,String password,String token){
        String uToken = (String) redisTemplate.opsForValue().get(username + "_token");
        if (uToken.equals(token)){
            //令牌认证成功，可以修改密码
            userService.updatePassword(username,password);
            //删除凭证
            redisTemplate.delete(username+"_token");
            return "redirect:/sso/tologin";
        }

        return "succ";
    }


    //登录！
    @RequestMapping("/login")
    public String Login(@CookieValue(value = "cartToken",required = false) String cartToken,User user,String returnUrl, HttpServletResponse response){
        user = userService.login(user);
        if (user == null){
            //登录失败
            return "redirect:/sso/tologin?error=1";
        }


        if (returnUrl==""){
            returnUrl = "http://localhost:8081";
        }
        //登录成功生成令牌
        String token = UUID.randomUUID().toString();
        //将用户信息写入redis
        redisTemplate.opsForValue().set(token,user);
        //设置过期时间
        redisTemplate.expire(token,7,TimeUnit.DAYS);
        //将用户信息写入cookie
        Cookie cookie = new Cookie("loginToken",token);
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath("/");
        response.addCookie(cookie);

        //登录成功合并购物车
        int result = cartService.mergeCarts(cartToken,user);
        if (result ==1){
            Cookie cookie1 = new Cookie("cartToken","");
            cookie1.setMaxAge(0);
            cookie1.setPath("/");
            response.addCookie(cookie1);
        }
        return "redirect:"+returnUrl;

    }

    /*验证是否登录*/
    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(@CookieValue(name = "loginToken",required = false) String loginToken, String callback){

        User user = null;
        if (loginToken != null){
            //验证是否登录
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }
        //应对非json的问题
        String userJson = (user != null) ? JSON.toJSONString(user):null;
        return callback != null ? callback+"("+userJson+")":userJson;
    }

    /*
    注销
    */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken",required = false) String loginToken,HttpServletResponse response){
        if (loginToken != null){
            //删除令牌
            redisTemplate.delete(loginToken);
        }
        //清除cookie
        Cookie cookie = new Cookie("loginToken","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/sso/tologin";
    }
}
