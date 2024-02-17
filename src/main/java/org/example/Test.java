package org.example;

import org.example.annotations.RecoverException;
import org.example.annotations.Timer;
import org.springframework.stereotype.Component;

@Timer
@Component
public class Test {
    int res;
    public int method1() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            res += i;
        }
        return res;
    }

    public void method2() throws InterruptedException {
        System.out.println("Hello world");
    }

    @RecoverException(noRecoverFor = {IllegalArgumentException.class})
    public void throwException() {
        throw new IllegalArgumentException();
    }
    @RecoverException(noRecoverFor = {NullPointerException.class})
    public void throwException2() {
        throw new IllegalArgumentException();
    }
}
