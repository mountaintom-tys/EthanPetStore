package com.petstore.dao;

import com.petstore.entity.Types;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypesDao {

    Types selectById(Integer typeId);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取列表
     * @return
     */
    @Select("select * from types order by id desc")
    List<Types> getList();
}
