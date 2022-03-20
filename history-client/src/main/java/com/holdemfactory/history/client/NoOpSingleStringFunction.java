package com.holdemfactory.history.client;

import org.springframework.stereotype.Component;

@Component("noopFunction")
public class NoOpSingleStringFunction implements StringFunction {

    @Override
    public String apply(String s) {
    	System.out.println("NoOpSingleStringFunction applies to s : "+s);
        return s;
    }
}