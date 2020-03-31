package com.vsj.model.response;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: HuaS
 * @Date :2019/8/2 17:02
 * @Describe:会员列表的返回值
 */
@Data
public class MemberResponse implements Serializable {
    @Excel(name = "会员id", orderNum = "0",width = 30)
    private Integer id;
    private Integer regId;
    @Excel(name = "会员昵称", orderNum = "1",width = 30)
    private String nickName;
    @Excel(name = "会员性别", orderNum = "2",width = 30)
    private Byte sex;
    @Excel(name = "推荐人", orderNum = "3",width = 30)
    private String parentName;//别名
    @Excel(name = "推荐人Id", orderNum = "4",width = 30)
    private Integer parentId;//上级id
    @Excel(name = "电话", orderNum = "5",width = 30)
    private String telPhone;
    @Excel(name = "可提现余额", orderNum = "6",width = 30)
    private Double carryBalance;
    @Excel(name = "冻结余额", orderNum = "7",width = 30)
    private Double freezeBalance;
    private Integer levelId;
    @Excel(name = "会员级别", orderNum = "8",width = 30)
    private String levelName;
    @Excel(name = "加入时间", orderNum = "9",width = 30)
    private String joinTime;
    @Excel(name = "备注", orderNum = "10",width = 30)
    private String remark;
    private String headUrl;

}
