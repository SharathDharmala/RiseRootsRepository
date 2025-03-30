package com.riseroots;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;

public class AppLifecycleBean {
    void onStart(@Observes StartupEvent ev) {
        System.out.println("Application is starting...");
    }
}
