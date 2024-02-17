package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner {

    @Autowired
    private Test test;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws InterruptedException {
        test.method1();
        test.method2();
        test.throwException();
        test.throwException2();
    }
}
