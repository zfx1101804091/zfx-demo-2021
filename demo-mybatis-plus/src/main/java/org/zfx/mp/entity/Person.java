package org.zfx.mp.entity;

import lombok.Data;
import org.zfx.mp.base.BaseEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Person)实体类
 *
 * @author makejava
 * @since 2021-02-25 22:23:34
 */
@Data
public class Person extends BaseEntity {
    private static final long serialVersionUID = -43877601006487928L;

    private String name;

    private Integer age;

    private String sex;

    private String address;

}
