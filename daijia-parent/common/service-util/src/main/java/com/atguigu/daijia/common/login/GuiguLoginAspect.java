package com.atguigu.daijia.common.login;

import com.atguigu.daijia.common.constant.RedisConstant;
import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.common.util.AuthContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.result.condition.RequestConditionHolder;

@Aspect
@Component
public class GuiguLoginAspect {

    @Autowired
    private RedisTemplate redisTemplate;
    //环绕通知

    /**
     * 自定义切面
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.atguigu.daijia.*.controller.*.*(..))&& @annotation(Login)")
    public Object login(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1.获取request对象
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
        HttpServletRequest request = sra.getRequest();
        //2.获取请求头的token
        String token = request.getHeader("token");

        //3.判断token是否空
        if(!StringUtils.hasText(token)){
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }
         //4.不为空
        String customerId = (String)redisTemplate.opsForValue().
                get(RedisConstant.USER_LOGIN_KEY_PREFIX+token);
        if(!StringUtils.hasText(customerId)){
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }
        //5.查询redis
        if(StringUtils.hasText(token)){
            AuthContextHolder.setUserId(Long.parseLong(customerId));
        }

        return proceedingJoinPoint.proceed();
    }
}
