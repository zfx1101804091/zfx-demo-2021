package org.zfx.mp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("teacher")
@Data
public class Teacher {
    private Integer id;
    private String name;
}
