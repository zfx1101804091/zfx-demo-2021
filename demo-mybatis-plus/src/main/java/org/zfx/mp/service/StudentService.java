package org.zfx.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zfx.mp.pojo.Student;

import java.util.List;

public interface StudentService extends IService<Student> {

    Student queryById();

    List<Student> queryAll();

    List<Student> queryMore();
}
