package com.nts.aspect;

import com.nts.annotation.LogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Around("@annotation(com.nts.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {
        /*
            日志格式： 谁 时间 时间 成功与否
         */
        // 谁
        String admin = (String) request.getSession().getAttribute("admin");
        // 时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        // 获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        // 获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status = "success";
            String logInfo = admin + "\t" + format + "\t" + "调用" + name + "方法" + "\t" + value + "\t" + status;
            // 像redis中添加日志信息
            HashOperations<String, Object, Object> log = stringRedisTemplate.opsForHash();
            log.put("持明法舟日志信息", admin + format, logInfo);
            // 像txt文本中写入日志信息
            String realPath = request.getServletContext().getRealPath("log/logInfo.txt");
            Writer out = new FileWriter(realPath, true);
            BufferedWriter bw = new BufferedWriter(out);
            bw.write(logInfo);
            bw.newLine();
            bw.close();
            out.close();
            return proceed;
        } catch (Throwable throwable) {
            String status = "error";
            System.out.println(admin + "\t" + format + "\t" + name + "\t" + status);
            throwable.printStackTrace();
            return null;
        }
    }
}
