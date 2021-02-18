package org.zfx.mp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("student")
@Data
public class Student {
    private Integer id;
    private String name;
    private Teacher teacher;
//    private List<Teacher> teacher;
}
