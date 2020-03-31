package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name="tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String name;// 商品名称
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架
    private Date createTime;// 创建时间

    @JsonIgnore //当数据返回页面时忽略此字段,此注解的依赖jar包<jackson-databind>
    private Date updateTime;// 最后修改时间

    @Transient //给通用Mapper说数据库表中没有此字段，是页面需要，临时添加的
    private String cname;
    @Transient
    private String bname;
    @Transient
    private List<Sku> skus;
    @Transient
    private SpuDetail spuDetail;
}
