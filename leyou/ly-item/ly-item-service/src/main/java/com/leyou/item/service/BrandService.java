package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page,Integer rows,String sortBy,
                                              Boolean desc,String key) {
        //分页
        PageHelper.startPage(page,rows);
        //搜索
        Example example = new Example(Brand.class);
        if(StringUtil.isNotEmpty(key)){
            example.createCriteria().orLike("name","%"+key+"%")
                                    .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if(StringUtil.isNotEmpty(sortBy)){
            String orderByClause = sortBy+ " " + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(brands);
        return new PageResult<>(info.getTotal(),brands);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int i = brandMapper.insert(brand);
        if(i==0){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        for(Long cid:cids){
            i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(i == 0 ){
                 throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
             }
        }

    }

    public Brand queryBrandById(Long id){
        Brand b = new Brand();
        b.setId(id);
        Brand brand = brandMapper.selectByPrimaryKey(b);
        if(brand == null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        return brand;
    }

    public List<Brand> queryBrandsByCid(Long cid) {
        List<Brand> brands = brandMapper.queryBrandsByCid(cid);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        return brands;
    }

    public List<Brand> queryBrandsByIds(List<Long> ids) {
        List<Brand> brands = new ArrayList<>();
        for(Long id : ids){
            Brand brand1 = brandMapper.selectByPrimaryKey(id);
            brands.add(brand1);
        }
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        return brands;
    }


}
