package org.springframework.samples.petclinic.proxy;

import org.springframework.util.StopWatch;

//weekyeon 프록시 패턴 만들어보기
public class CashPerf implements Payment{

    //Credit Card에 문제가 있으면 Cash로 콜백
    Payment cash = new Cash();

    @Override
    public void pay(int amount) {
        /*if(amount > 100){
            System.out.println(amount + " 신용 카드");
        }else{
            cash.pay(amount);
        }*/

        //프록시 패턴에 대해 조금 더 알아보자~!
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        cash.pay(amount);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
