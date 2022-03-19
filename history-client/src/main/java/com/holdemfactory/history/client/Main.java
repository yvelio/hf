package com.holdemfactory.history.client;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class App {
    public static void main(String ... args) {
        Quarkus.run(args);
        System.out.println("##################################################");
        System.out.println("			App starts...");
        System.out.println("##################################################");
    }
}
