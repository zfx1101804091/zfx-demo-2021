package org.zfx.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.zfx.mp.base.BaseEntity;

@TableName("student")
@Data
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;
    private Teacher teacher;

//    private List<Teacher> teacher;
}
