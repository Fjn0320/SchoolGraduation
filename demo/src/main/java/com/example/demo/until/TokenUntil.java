package com.example.demo.until;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUntil {

    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String createToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

//    /**
//     * 获取当前登录的用户信息
//     *
//     * @return user对象
//     */
    public static UserPojo getCurrentUser(){
        try{
            //获取当前请求的request
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            System.out.println("获取到请求的token：" + token);
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                System.out.println("在TokenUntil获取的userId：" +userId );
                UserPojo userPojo1 = staticUserService.selectById(Integer.valueOf(userId));
                System.out.println("查询到的用户信息：" + userPojo1);
                return  userPojo1;
            } else {
                System.out.println("没有获取到token");
            }

        }catch (Exception e){
            return null;
        }
        return null;
    }
}