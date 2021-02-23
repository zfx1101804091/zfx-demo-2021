package org.zfx.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.mp.pojo.Person1;
import org.zfx.mp.pojo.Person2;
import org.zfx.mp.pojo.Student;
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
    @GetMapping("ts5")
    public void test5() throws Exception {

        Person1 person1 = new Person1();
        person1.setName("p1");
        person1.setAddress("北京");
        person1.setHight(180);
        person1.setAge(19);
        person1.setTellPhone("11111111223");

        Person2 person2 = new Person2();
        person2.setName("p2");
        person2.setAddress("上海");
        person2.setHight(90);
        person2.setAge(7);
        person2.setTellPhone("444555523");

//        BeanUtils.copyProperties(person1,person2);
//        BeanUtil.copyProperties(person1,person2);
        Copy(person1,person2);
        System.out.println(person2.toString());

    }
    public static void Copy(Object source, Object dest) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < sourceProperty.length; i++) {

                for (int j = 0; j < destProperty.length; j++) {

                    if (sourceProperty[i].getName().equals(destProperty[j].getName())  && sourceProperty[i].getPropertyType() == destProperty[j].getPropertyType()) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(dest,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println(RandomUtil.randomString(12));
    }
}
