package ru.javawebinar.topjava.util;

import java.util.concurrent.locks.ReentrantLock;

public class CounterUtil {
    private static final ReentrantLock counterLock = new ReentrantLock(true); // enable fairness policy
    private static Integer counter = 1; // a global counter

    public static Integer incrementCounter() {
        counterLock.lock();

        // Always good practice to enclose locks in a try-finally block
        try {
            System.out.println(Thread.currentThread().getName() + ": " + counter);
            counter++;
            return counter;
        } finally {
            counterLock.unlock();
        }
    }
}
