package com.vsj.mapper;

import com.vsj.model.VsjOrderOperation;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * @Classname OrderOperationMapper
 * @Description 订单操作记录Mapper
 * @Date 2019/8/8 10:31
 * @Created by wangzx
 */
@Mapper
public interface OrderOperationMapper {

    @InsertProvider(type = OrderOperationMapper.OrderOperation.class,method = "insertOrderOperation")
    int insertOrderOperation(VsjOrderOperation vsjOrderOperation);

    @SelectProvider(type = OrderOperationMapper.OrderOperation.class,method = "selectOrderOperation")
    List<VsjOrderOperation> selectOrderOperation(VsjOrderOperation  vsjOrderOperation);

    class OrderOperation{

        public String selectOrderOperation(VsjOrderOperation  vsjOrderOperation){
            String sql = new SQL(){{
                SELECT("oo.operate_type,oo.crete_time,oo.remark,oo.reg_id,oo.sys_user_id,oo.platform_code");
                SELECT("r.nick_name,u.nick_name as nickNameSys");
                FROM("vsj_order_operation oo");
                LEFT_OUTER_JOIN("vsj_order o ON oo.order_id = o.order_id");
                LEFT_OUTER_JOIN("vsj_register r ON  oo.reg_id = r.id");
                LEFT_OUTER_JOIN("vsj_sys_user u ON oo.sys_user_id = u.id");
                WHERE("oo.order_id = #{orderId} and oo.platform_code = #{platformCode}");
                ORDER_BY("crete_time DESC");
            }}.toString();
            return sql;
        }

        public String insertOrderOperation(VsjOrderOperation vsjOrderOperation){
            String sql = new SQL(){{
                INSERT_INTO("vsj_order_operation");
                VALUES("order_id","#{orderId}");
                VALUES("operate_type","#{operateType}");
                VALUES("crete_time","NOW()");
                if(vsjOrderOperation.getRemark() != null){
                    VALUES("remark","#{remark}");
                }
                VALUES("reg_id","#{regId}");
                VALUES("platform_code","#{platformCode}");
                if(vsjOrderOperation.getSysUserId() != null){
                    VALUES("sys_user_id","#{sysUserId}");
                }
            }}.toString();
            return sql;
        }
    }
}
