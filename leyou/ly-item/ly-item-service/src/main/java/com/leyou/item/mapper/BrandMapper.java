package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Select("SELECT b.*\n" +
            "FROM tb_brand b INNER JOIN tb_category_brand cb \n" +
            "ON b.id = cb.brand_id\n" +
            "WHERE cb.category_id = #{cid}")
    public List<Brand> queryBrandsByCid(@Param("cid") Long cid);

}
