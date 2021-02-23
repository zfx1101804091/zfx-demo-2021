package org.zfx.mp.base;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface BaseService<T> extends IService<T> {
    boolean deleteLogic(@NotEmpty List<Long> ids);
}
