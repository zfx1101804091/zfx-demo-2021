package org.zfx.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zfx.mp.entity.Person;


public interface PersonMapper extends BaseMapper<Person> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Person queryById(Long id);


}

