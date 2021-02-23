package org.zfx.mp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.zfx.mp.base.BaseEntity;

@TableName("teacher")
@Data
public class Teacher extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String name;
}
