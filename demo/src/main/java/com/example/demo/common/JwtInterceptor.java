package com.example.demo.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//设置拦截器
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");//获取header里的token的值  也称为钥匙
        if (StrUtil.isBlank(token)){
            token = request.getParameter("token");//url参数 ？token=xxx
        }

        // 如果不是映射到方法直接通过
        if (handler instanceof HandlerMethod) {
            //这个方法是当AuthAccess这个注解在那个方法上面就可以不用通过token直接访问
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        //执行认证  然后再判断token是否为空，如果为空则抛出异常
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Wrapper.NOT_LOGIN_CODE,Wrapper.NOT_LOGIN_MESSAGE);//抛出401 传递的信息为请登录
        }
        //然后获取token的id
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);//JWT中decode存放token然后进行解码  getAudience()是个集合方法，获取第一个
        }catch (JWTException e){
            throw new ServiceException(Wrapper.NOT_LOGIN_CODE,Wrapper.NOT_LOGIN_MESSAGE);//抛出401 传递的信息为请登录
        }

        //然后根据id查询数据库 然后获取相关的类
        UserPojo userPojo = userMapper.selectById(Integer.valueOf(userId));
        if (userPojo == null){
            throw new ServiceException(Wrapper.NOT_LOGIN_CODE,Wrapper.NOT_LOGIN_MESSAGE);//抛出401 传递的信息为请登录
        }

        //通过用户密码加密之后生成一个验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userPojo.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTException e){
            throw new ServiceException(Wrapper.NOT_LOGIN_CODE,Wrapper.NOT_LOGIN_MESSAGE);//抛出401 传递的信息为请登录
        }

        return true;
    }
}
