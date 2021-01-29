package org.zfx.netty.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VenusResponse {
    private long id;
    private String venus;
    private Long sessionId;
    private String transaction;
    private Long sender;
    private Object pluginData;
}
