package org.zfx.mp.service;

import org.zfx.mp.base.BaseService;
import org.zfx.mp.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student> {

    Student queryById();

    List<Student> queryAll();

    List<Student> queryMore();
}
