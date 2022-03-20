package com.holdemfactory.history.client;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.springframework.scheduling.annotation.Scheduled;

@ApplicationScoped              
public class CounterBean {

    private AtomicInteger counter = new AtomicInteger();

    public int get() {   
        return counter.get();
    }

//    @Scheduled(cron="*/5 * * * * ?")   
//    void cronJob() {
//        counter.incrementAndGet();      
//        System.out.println("Cron expression hardcoded");
//    }
//
//    @Scheduled(cron = "{cron.expr}")   
//    void cronJobWithExpressionInConfig() {
//        counter.incrementAndGet();
//        System.out.println("Cron expression configured in application.properties");
//    }

    /**
     * Execute the annotated method with a fixed period in milliseconds between invocations.
     */
    @Scheduled(fixedRate = 1000)    
    void jobAtFixedRate() {
        int count = counter.incrementAndGet();
        System.out.println("count on fixed Rate expression: "+count);
    }

//    @Scheduled(fixedRateString = "${fixedRate.expr}")      
//    void jobAtFixedRateInConfig() {
//        counter.incrementAndGet();
//        System.out.println("Fixed Rate expression configured in application.properties");
//    }
}