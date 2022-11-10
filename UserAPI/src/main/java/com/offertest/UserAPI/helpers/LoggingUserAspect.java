package com.offertest.UserAPI.helpers;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggingUserAspect {


    private static final Logger logger = LoggerFactory.getLogger(LoggingUserAspect.class);


    @Around("execution(* com.offertest.UserAPI.controllers.UserAPI.*(..) )")
    public Object callOnController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logDataFromUserAPI(proceedingJoinPoint);
    }

    /*
    @Around("execution(* com.offertest.UserAPI.services.UserService.*(..) )")
    public Object callOnService( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        return logDataFromUserAPI(proceedingJoinPoint);
    }
*/

    private Object logDataFromUserAPI(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();

        Object[] args = proceedingJoinPoint.getArgs();
        Object[] argsName = codeSignature.getParameterNames();


        try {
            logger.debug("Methode : " + codeSignature);
            for (int i = 0; i < args.length; i++) {
                logger.debug("parameter " + i + " : " + argsName[i] + " = " + args[i]);
            }
            long startTime = System.currentTimeMillis();
            Object value = proceedingJoinPoint.proceed(); // call methode addUser
            long endTime = System.currentTimeMillis();
            logger.debug("Time : " + ((endTime - startTime)) + " milliseconds");
            logger.debug("return value : " + value);
            return value;
        } catch (Throwable e) {
            logger.error("Error : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

