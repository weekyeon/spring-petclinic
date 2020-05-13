package org.springframework.samples.petclinic.proxy;

//weekyeon 프록시 패턴 만들어보기 : 인터페이스를 사용하는 클라이언트쪽 코드
public class Store {

    Payment payment;

    public Store(Payment payment){
        this.payment = payment;
    }

    public void buySomething(int amount){
        payment.pay(amount);
    }
}

/*
    스토어 입장에서는 페이먼트라는 인터페이스만 쓰지만,
    페이먼트에 캐쉬가 아니라 CashPerf를 주면
    CashPerf가 캐쉬를 쓸지 크레딧카드를 쓸지 알아서 판단하는 것
    다시 말해서, 클라이언트쪽 코드는
 */
