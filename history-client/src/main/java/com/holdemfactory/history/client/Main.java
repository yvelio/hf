package com.holdemfactory.history.client;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String ... args) {
        Quarkus.run(args);
        System.out.println("##################################################");
        System.out.println("			Main starts...");
        System.out.println("##################################################");
    }
}
