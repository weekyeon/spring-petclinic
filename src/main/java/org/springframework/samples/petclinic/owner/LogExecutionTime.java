package org.springframework.samples.petclinic.owner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//weekyeon @LogExecutionTime 어노테이션 만들기

@Target(ElementType.METHOD) //이 어노테이션을 메소드에 쓰겠다고 타겟 설정해서 알려줘야함
@Retention(RetentionPolicy.RUNTIME) //이 어노테이션을 언제까지 유지할 것인지를 설정해주어야 함
public @interface LogExecutionTime {
}
