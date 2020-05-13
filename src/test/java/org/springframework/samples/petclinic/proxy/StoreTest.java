package org.springframework.samples.petclinic.proxy;

import org.junit.jupiter.api.Test;

public class StoreTest {

    @Test
    public void testPay(){
        //Store는 Cash를 쓰고 있었을 것. 그래서 성능 측정이 되지 않았을 것.
            //Payment cash = new Cash();
            //Store store = new Store(cash);
            //store.buySomething(100);
        //그런데 우리가 CashPerf라는 프록시 코드를 만들었고
        //페이먼트라는 인터페이스에 CashPerf를 사용하도록 바꿨기 때문에
        //기존 코드를 건들지 않고 성능 측정이 됨
        //새로운 코드를 추가했지만 기존 코드를 건들지 않았다는 것이 중요함
        //이게 AOP를 프록시 패턴으로 구현하는 방법
        //이런 일들이 스프링 AOP에서는 자동으로 이루어짐
        Payment cashPerf = new CashPerf();
        Store store = new Store(cashPerf);
        store.buySomething(100);

        /*
            @Transactional 어노테이션이 있으면
            OwnerRepository에 해당 객체 타입의 프록시가 만들어짐(트랜잭션이 앞뒤에 붙어있는 거)
            원래 JDBC 트랜잭션 처리는,
                원래 우리가 해야 하는 SQL문 앞 뒤에 코드가 붙게 됨
                setAutoCommit == FALSE, SQL문 실행, commit, rollback 등 처리를 해주어야 함
                이러한 코드를 생략해주는 게 @Transactional
            @Transactional을 사용하는 게 지금 만든 프록시 패턴과 동일함
         */
    }
}
