package org.zfx.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zfx.mp.mapper.StudentMapper;
import org.zfx.mp.pojo.Student;
import org.zfx.mp.service.StudentService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student queryById() {
        return studentMapper.queryById();
    }

    @Override
    public List<Student> queryAll() {
        return studentMapper.selectList(null);
    }

    @Override
    public List<Student> queryMore() {
        return studentMapper.queryMore();
    }
}
