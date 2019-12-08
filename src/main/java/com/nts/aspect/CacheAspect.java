package com.nts.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Aspect
@Configuration
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.nts.annotation.SelectCache)")
    /**
     * 查询方法加入环绕通知
     * 现在缓存中查询 若果没有 则调用查询方法 再把结果加入缓存中
     * 存储结构采用HashMap  redis中大Key --> 类名 小key -->方法名+参数
     */
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("------------------------进入查询添加缓存------------------------");
        // 获得类名  大Key
        String bKey = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        System.out.println("类名，redis中的Key：" + bKey);
        // 获得方法名
        String method = proceedingJoinPoint.getSignature().getName();
        // 获得参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 将参数数组转为String字符串 拼上 方法名 得到小key
        String s = Arrays.toString(args);
        String sKey = method + s;
        HashOperations<String, Object, Object> map = redisTemplate.opsForHash();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 保证反序列化成功 将转换对象类型存到json中
        ObjectMapper objectMapper = new ObjectMapper();
        // 转换json时将原始类型保留在json中
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 修改日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置序列化key方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置序列化value方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 判断redis中查询结果是否存在
        Boolean aBoolean = map.hasKey(bKey, sKey);
        if (aBoolean) {
            System.out.println("-------------------从缓存中取数据------------------------");
            return map.get(bKey, sKey);
        }
        // 返回值 执行目标方法
        Object proceed = proceedingJoinPoint.proceed();
        // 添加进redis
        map.put(bKey, sKey, proceed);
        System.out.println("-----------------------缓存结束----------------");
        return proceed;
    }

    /**
     * 在进行增删改操作时 把redis中的数据清空
     */
    @Around("@annotation(com.nts.annotation.EditCache)")
    public Object deleteCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("进入删除redis----------------------");
        Object proceed = proceedingJoinPoint.proceed();
        // 获得类名 大key
        String bKey = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        // 对大Key序列化
        //StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 删除数据
        redisTemplate.delete(bKey);
        return proceed;
    }
}
