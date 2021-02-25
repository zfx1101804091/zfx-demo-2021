package org.zfx.mp.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.mp.entity.Person;
import org.zfx.mp.entity.Student;
import org.zfx.mp.service.StudentService;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private StudentService studentService;

    @GetMapping("ts")
    public String test1() {
        Student student = studentService.queryById();
        return JSONUtil.parseObj(student).toString();
    }

    @GetMapping("ts2")
    public String test2() {
        List<Student> student = studentService.queryAll();
        return JSONUtil.parseObj(student).toString();
    }

    /**
     * 一对多
     *
     * @return
     */
    @GetMapping("ts3")
    public String test3() {
        List<Student> student = studentService.queryMore();
        student.forEach(student1 -> {
            System.out.println(student1);
        });

        return JSONUtil.parseArray(student).toString();
    }

    @GetMapping("ts4")
    public void insert() {
        Student student = new Student();
        student.setName("测试王"+RandomUtil.randomString(12));
//        QueryWrapper<Student> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", 5);
//        studentService.update(student, wrapper);
        studentService.save(student);
    }
    @GetMapping("ts6")
    public void update() {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 1364228172695216130L);
        Student student = studentService.getOne(wrapper);
        student.setName("hahaha");
        studentService.updateById(student);
    }


    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(12));
    }
}
