package org.springframework.samples.petclinic.proxy;

//weekyeon 프록시 패턴 만들어보기 : 기본적인 pay 방법
public class Cash implements Payment{
    @Override
    public void pay(int amount) {
        System.out.println(amount + " 현금 결제");
    }
}
