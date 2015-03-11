package com.apwglobal.allegro.server.aop;

import com.apwglobal.nice.exception.AllegroException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AllegroExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(AllegroExceptionHandler.class);

    @Around("bean(allegro) && !execution(public java.lang.String *.toString())")
    public Object syncing(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        try {
            logger.debug("Executing: {}", pjp.getSignature().getName());
            retVal = pjp.proceed();
        } catch (AllegroException e) {
            logger.error("Executing: {}, error = {}, ex = {}", pjp.getSignature().getName(), e.getMessage(), e);
        }
        return retVal;
    }

}
