package com.vsj.material.mapper;

import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.*;
import sun.plugin2.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    public static final String tableName = "vsj_user";

    public static final String baseColumn = "id,name,phone,platform_code";

    @InsertProvider(type =UserMapper.Provider.class,method = "insert")
    int insert(User user);


    class Provider{
        public String insert(User user){
            String sql = new SQL() {{
                INSERT_INTO(tableName);
                VALUES("name","#{name}");
                if(StringUtil.isNoEmptyStr(user.getPhone())){
                    VALUES("phone","#{phone}");
                }
                VALUES("platform_code","#{platformCode}");
            }
            }.toString();
            return sql;
        }



    }
}
