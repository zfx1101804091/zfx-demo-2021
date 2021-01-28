package com.zfx.learn.client;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HashWheelTimerTest {
    public static void main(String[] argv) {
//        final Timer timer = new HashedWheelTimer(20,TimeUnit.SECONDS);
//        timer.newTimeout(new TimerTask() {
//            public void run(Timeout timeout) throws Exception {
//                System.out.println("timeout 5");
//            }
//        }, 5, TimeUnit.SECONDS);
//        timer.newTimeout(new TimerTask() {
//            public void run(Timeout timeout) throws Exception {
//                System.out.println("timeout 10");
//            }
//        }, 10, TimeUnit.SECONDS);



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.MILLISECONDS,8);
        HashedWheelTimer timer = new HashedWheelTimer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("hello world" + LocalDateTime.now().format(formatter));
                timer.newTimeout(this, 2, TimeUnit.SECONDS);
            }
        };

//        TimerTask timerTask2 = new TimerTask() {
//            @Override
//            public void run(Timeout timeout) throws Exception {
//                float error = 3 / 2;
//                System.out.println(error);
//                timer.newTimeout(this, 2, TimeUnit.SECONDS);
//            }
//        };
        timer.newTimeout(timerTask, 6, TimeUnit.SECONDS);
//        timer.newTimeout(timerTask2, 4, TimeUnit.SECONDS);
        System.out.println("------");
    }
}