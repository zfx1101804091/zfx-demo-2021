package org.zfx.mp.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    public BaseServiceImpl() {
    }

    public boolean save(T entity) {
   /*     BladeUser user = SecureUtil.getUser();
        if (user != null) {
            entity.setCreateUser(user.getUserId());
            entity.setUpdateUser(user.getUserId());
        }

        Date now = DateUtil.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }*/
        entity.setStatus(1);
        entity.setCreateUser("zansan");
        entity.setUpdateUser("zansan");
        entity.setIsDeleted(0);
        return super.save(entity);
    }

    public boolean updateById(T entity) {
       /* BladeUser user = SecureUtil.getUser();
        if (user != null) {
            entity.setUpdateUser(user.getUserId());
        }

        entity.setUpdateTime(DateUtil.now());*/
        entity.setUpdateUser("lisi");
        return super.updateById(entity);
    }

    public boolean deleteLogic(@NotEmpty List<Long> ids) {
        return super.removeByIds(ids);
    }
}
