package org.zfx.netty.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicLong;

@Data
@Accessors(chain = true)
public class VenusRequest {
    //保证id唯一
    private final long id;
    private String url;
    private Object content;

    public static final AtomicLong nid = new AtomicLong(0);

    public VenusRequest() {
        this.id=nid.incrementAndGet();
    }

}
