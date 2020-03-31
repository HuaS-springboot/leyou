package com.vsj.mapper;

import com.vsj.model.VsjSysAreas;

import com.vsj.model.request.BaseQueryStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Classname SysAreaMapper
 * @Description TODO
 * @Date 2019/7/25 15:18
 * @Created by wangzx
 */
@Mapper
public interface SysAreaMapper {

    @Select("select id,code,parent_code,name,province,city,district,full_name,grade from vsj_sys_areas")
    List<VsjSysAreas> getSysAreas();
}
