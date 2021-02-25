package org.zfx.mp.service.impl;

import org.zfx.mp.base.BaseServiceImpl;
import org.zfx.mp.entity.Person;
import org.zfx.mp.entity.Student;
import org.zfx.mp.mapper.PersonMapper;
import org.zfx.mp.mapper.StudentMapper;
import org.zfx.mp.service.PersonService;
import org.springframework.stereotype.Service;
import org.zfx.mp.service.StudentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Person)表服务实现类
 *
 * @author makejava
 * @since 2021-02-25 22:23:38
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<PersonMapper, Person> implements PersonService {
    @Resource
    private PersonMapper personMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Person queryById(Long id) {
        return this.personMapper.queryById(id);
    }

}
