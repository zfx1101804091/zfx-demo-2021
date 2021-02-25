package org.zfx.mp.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.zfx.mp.entity.Person;
import org.zfx.mp.service.PersonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Random;

/**
 * (Person)表控制层
 *
 * @author makejava
 * @since 2021-02-25 22:23:38
 */
@RestController
@RequestMapping("person")
public class PersonController {
    /**
     * 服务对象
     */
    @Resource
    private PersonService personService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("info")
    public Person selectOne(Long id) {
        return this.personService.queryById(id);
    }

    /**
     * 插入
     * @return
     */
    @PostMapping("insert")
    public String insert(@RequestBody Person person){
        boolean flag = personService.save(person);
        return String.valueOf(flag);
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody Person person,@PathVariable Long id){
        person.setId(id);
        personService.updateById(person);
    }

    @DeleteMapping("/del/{id}")
    public void del(@PathVariable("id") Long id){
        personService.deleteLogic(Arrays.asList(id));
    }

}
