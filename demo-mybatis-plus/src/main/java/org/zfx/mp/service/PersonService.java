package org.zfx.mp.service;

import org.zfx.mp.base.BaseService;
import org.zfx.mp.entity.Person;
import org.zfx.mp.entity.Student;

import java.util.List;

/**
 * (Person)表服务接口
 *
 * @author makejava
 * @since 2021-02-25 22:23:38
 */
public interface PersonService extends BaseService<Person> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Person queryById(Long id);

}
