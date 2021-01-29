package org.zfx.netty.core;

import lombok.Data;
import lombok.experimental.Accessors;
import org.zfx.netty.model.VenusRequest;
import org.zfx.netty.model.VenusResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管理所有future
 */
@Data
@Accessors(chain = true)
public class DefaultChannelFure {

    private long id;
    private VenusResponse response;

    //锁
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static Map<Long, DefaultChannelFure> FUTURE = new ConcurrentHashMap<>();

    public DefaultChannelFure() {
    }

    public DefaultChannelFure(VenusRequest request) {
        id=request.getId();
    }

    public VenusResponse get(){
        lock.lock();
        while (!hasDone()){
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        return response;
    }

    //收到服务器响应
    public static void recive(VenusResponse response){
        //找到response相对应的DefaultChannelFure
        DefaultChannelFure dcf = FUTURE.remove(response.getId());

    }

    private boolean hasDone() {
        return response!=null?true:false;
    }
}
