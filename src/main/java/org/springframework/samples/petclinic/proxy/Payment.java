package org.springframework.samples.petclinic.proxy;

//weekyeon 프록시 패턴 만들어보기 : 서비스의 인터페이스 생성
public interface Payment {
    void pay(int amount);
}
