package org.zfx.mp.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.zfx.mp.base.BaseEntity;

import java.util.Date;

@TableName("student")
@Data
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;
//    private Teacher teacher;

//    private List<Teacher> teacher;
}
