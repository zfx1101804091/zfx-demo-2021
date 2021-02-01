package org.zfx.netty.core;

import lombok.Data;
import lombok.experimental.Accessors;
import org.zfx.netty.model.VenusRequest;
import org.zfx.netty.model.VenusResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管理所有future
 */
@Data
@Accessors(chain = true)
public class DefaultChannelFuture {

    private long id;
    private volatile VenusResponse response;

    //锁
    private volatile Lock lock = new ReentrantLock();
    private volatile Condition condition = lock.newCondition();

    public static Map<Long, DefaultChannelFuture> FUTURE = new ConcurrentHashMap<>();

    private long timeout;
    private final long start = System.currentTimeMillis();

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public DefaultChannelFuture() {
    }

    public DefaultChannelFuture(VenusRequest request) {
        id = request.getId();
        FUTURE.put(id, this);
    }

    //response无值时才去获取
    public VenusResponse get() {
        lock.lock();
        while (!hasDone()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return response;
    }

    public VenusResponse get(long timeout) {
        lock.lock();
        while (!hasDone()) {
            try {
                condition.await();
                if (System.currentTimeMillis() - start > timeout) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return response;
    }

    //收到服务器响应
    public static void recive(VenusResponse response) {
        //找到response相对应的DefaultChannelFure
        DefaultChannelFuture future = FUTURE.remove(response.getId());
        if (future == null) {
            return;
        }
        Lock lock = future.getLock();
        lock.lock();
        try {
            future.setResponse(response);
            Condition condition = future.getCondition();
            if (condition != null) {
                condition.signal();//唤醒
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private boolean hasDone() {
        return response != null ? true : false;
    }

    //继续优化
    static class FutureTimeOutThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (Long futureId : FUTURE.keySet()) {
                    DefaultChannelFuture future = FUTURE.get(futureId);
                    if(future==null){
                        FUTURE.remove(futureId);
                        continue;
                    }
                    if (future.getTimeout() > 0) {
                        if (System.currentTimeMillis() - future.getStart() > future.getTimeout()) {
                            VenusResponse response = new VenusResponse();
                            response.setId(UUID.randomUUID().timestamp());
                            response.setTransaction("请求超时了！！");
                            DefaultChannelFuture.recive(response);
                        }
                    }
                }
            }
        }
    }

    static {
        FutureTimeOutThread futureTimeOutThread = new FutureTimeOutThread();
        futureTimeOutThread.setDaemon(true);
        futureTimeOutThread.start();
    }
}
