package com.atguigu.daijia.customer.service.impl;

import com.atguigu.daijia.common.constant.RedisConstant;
import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.Result;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.customer.client.CustomerInfoFeignClient;
import com.atguigu.daijia.customer.service.CustomerService;
import com.atguigu.daijia.model.vo.customer.CustomerLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerInfoFeignClient client;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 小程序登录
     * @param code
     * @return
     */
    @Override
    public String login(String code) {
        //1.code进行远程调用，返回id
        Result<Long> login = client.login(code);
        //2.返回失败，返回错误提示
        Integer codeResult = login.getCode();
        if(codeResult!=200){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        //3.获取远程调用生成id
        Long customerId = login.getData();
        //4.判断id是否为空，返回错误提示
        if(customerId==null){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        //5.生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //6.把用户id放入redis，设置过期时间
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY_PREFIX+token,
                customerId.toString(),
                RedisConstant.USER_LOGIN_KEY_TIMEOUT,
                TimeUnit.SECONDS);

        //7.返回token
        return token;
    }


    /**
     * 获取用户登录信息
     * @param customerId
     * @return
     */
    @Override
    public CustomerLoginVo getCustomerLoginInfo(long customerId) {
        //远程调用
        Result<CustomerLoginVo> result = client.getCustomerLoginInfo(customerId);
        //异常处理
        if(result.getCode().intValue() != 200) {
            throw new GuiguException(result.getCode(), result.getMessage());
        }
        CustomerLoginVo customerLoginVo = result.getData();
        if(null == customerLoginVo) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        return customerLoginVo;
    }

}
