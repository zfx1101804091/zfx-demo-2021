package org.zfx.mp.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("student")
@Data
public class Student {
    private Integer id;
    private String name;
    private Teacher teacher;

    //@TableField 注意！这里需要标记为填充字段
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

//    private List<Teacher> teacher;
}
