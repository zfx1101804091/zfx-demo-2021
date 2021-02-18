package org.zfx.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zfx.mp.pojo.Student;

import java.util.List;

public interface  StudentMapper extends BaseMapper<Student> {
     Student queryById();

     List<Student> queryMore();
}
