package org.springframework.samples.petclinic.owner;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//weekyeon @LogExecutionTime를 읽어서 성능을 측정할 수 있는 Aspect 생성
@Component
@Aspect
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //Around 어노테이션 안에서 JoinPoint라는 파라미터를 받을 수 있다.
        //JoinPoint : @LogExecutionTime이 붙어있는 메소드
        //JoinPoint를 받으면, 해당 인터페이스 타입으로 들어오고

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //JoinPoint로 들어온 인터페이스를 실행하겠다
        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        logger.info(stopWatch.prettyPrint());
        return proceed;

        //즉, 위의 코드를 LogExecutionTime 어노테이션이 붙은 코드 주변에
        //이 코드를 적용하겠다고 알려주는 것
        //이게 Aspect이고
        //스프링 AOP이다.
        //프록시 패턴을 기반으로 동작한다.
    }

}
