package org.zfx.mp.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.mp.pojo.Student;
import org.zfx.mp.pojo.Teacher;
import org.zfx.mp.service.StudentService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private StudentService studentService;

    @GetMapping("ts")
    public String test1(){
       Student student = studentService.queryById();
       return JSONUtil.parseObj(student).toString();
    }

    @GetMapping("ts2")
    public String test2(){
        List<Student> student = studentService.queryAll();
        return JSONUtil.parseObj(student).toString();
    }

    /**
     * 一对多
     * @return
     */
    @GetMapping("ts3")
    public String test3(){
        List<Student> student = studentService.queryMore();
        student.forEach(student1 -> {
            System.out.println(student1);
        });

        return JSONUtil.parseArray(student).toString();
    }

    @GetMapping("ts4")
    public void insert(){
        Student student = new Student();
        student.setId(0);
        student.setName("测试王33");
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id",0);
        studentService.update(student, wrapper);
    }

    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(12));
    }
}
