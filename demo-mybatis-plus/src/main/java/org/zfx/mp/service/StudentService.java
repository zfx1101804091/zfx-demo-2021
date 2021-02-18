package org.zfx.mp.service;

import org.zfx.mp.pojo.Student;

import java.util.List;

public interface StudentService {

    Student queryById();

    List<Student> queryAll();

    List<Student> queryMore();
}
