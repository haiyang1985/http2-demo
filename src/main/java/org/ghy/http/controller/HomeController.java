package org.ghy.http.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class HomeController {
    private static final AtomicBoolean flag = new AtomicBoolean(false);
    private static final AtomicInteger count = new AtomicInteger();

    @RequestMapping("/")
    public String home() {
        int index = count.incrementAndGet();
        System.out.println("This is " + index + " before CAS.");
        System.out.println(Thread.currentThread().getName());
        if (index % 2 == 1) {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("This is " + index + " after CAS");
        return "home";
    }
}
