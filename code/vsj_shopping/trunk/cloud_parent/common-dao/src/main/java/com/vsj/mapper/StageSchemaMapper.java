package com.vsj.mapper;

import com.vsj.model.VsjStageSchema;
import com.vsj.model.request.BaseQueryStat;
import com.vsj.model.response.StageSchemaLevelInfo;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Classname StageSchemaMapper
 * @Description TODO
 * @Date 2019/7/26 17:26
 * @Created by wangzx
 */
@Mapper
public interface StageSchemaMapper {
	
	public static final String tableName = "vsj_stage_schema ss";

	public static final String baseColumn = "ss.id,ss.level_id,ss.open_peers,ss.bonus_num,ss.bonus_units,ss.peers_num,ss.peers_hierarchy,ss.perrs_units,ss.product_id,ss.platform_code";

    @InsertProvider(type = StageSchemaMapper.StageSchemaProvider.class,method = "insertStageSchema")
    int insertStageSchema(VsjStageSchema vsjStageSchema);

    @UpdateProvider(type = StageSchemaMapper.StageSchemaProvider.class,method = "updateStageSchema")
    int updateStageSchema(VsjStageSchema vsjStageSchema);

    @Delete("delete from vsj_stage_schema where id = #{id} and platform_code = #{platformCode}")
    int deleteStageSchema(BaseQueryStat queryStat);
    
    @SelectProvider(type = StageSchemaMapper.StageSchemaProvider.class,method = "selectStageSchema")
    List<VsjStageSchema> selectStageSchema(@Param("productId")Integer productId,@Param("platformCode")Integer platformCode);
    
    @SelectProvider(type = StageSchemaMapper.StageSchemaProvider.class,method = "selectHighLevelAndSchema")
    List<StageSchemaLevelInfo> selectHighLevelAndSchema(@Param("levelId")Integer levelId,@Param("productId")Integer productId,@Param("platformCode")Integer platformCode);

    @Delete("delete from vsj_stage_schema where product_id = #{productId} and platform_code = #{platformCode}")
    void deleteStageSchemaByProductId(BaseQueryStat baseQueryStat);

    @InsertProvider(type = StageSchemaMapper.StageSchemaProvider.class,method = "insertStageSchemaBatch")
    void insertStageSchemaBatch(List<VsjStageSchema> list);

    class StageSchemaProvider{
        public String insertStageSchema(VsjStageSchema vsjStageSchema){
            String sql = new SQL(){{
                INSERT_INTO("vsj_stage_schema");
                if(vsjStageSchema.getLevelId() != null){
                    VALUES("level_id","#{levelId}");
                }
                if(vsjStageSchema.getOpenPeers() != null){
                    VALUES("open_peers","#{openPeers}");
                }
                if(vsjStageSchema.getBonusNum() != null){
                    VALUES("bonus_num","#{bonusNum}");
                }
                if(vsjStageSchema.getBonusUnits() != null){
                    VALUES("bonus_units","#{bonusUnits}");
                }
                if(vsjStageSchema.getPeersNum() != null){
                    VALUES("peers_num","#{peersNum}");
                }
                if(vsjStageSchema.getPeersHierarchy() != null){
                    VALUES("peers_hierarchy","#{peersHierarchy}");
                }
                if(vsjStageSchema.getPerrsUnits() != null){
                    VALUES("perrs_units","#{perrsUnits}");
                }
                if(vsjStageSchema.getProductId() != null){
                    VALUES("product_id","#{productId}");
                }
                VALUES("platform_code","#{platformCode}");
            }}.toString();
            return sql;
        }

        public String updateStageSchema(VsjStageSchema vsjStageSchema){
            String sql = new SQL(){{
                UPDATE("vsj_stage_schema");
                if(vsjStageSchema.getLevelId() != null){
                    SET("level_id = #{levelId}");
                }
                if(vsjStageSchema.getOpenPeers() != null){
                    SET("open_peers = #{openPeers}");
                }
                if(vsjStageSchema.getBonusNum() != null){
                    SET("bonus_num = #{bonusNum}");
                }
                if(vsjStageSchema.getBonusUnits() != null){
                    SET("bonus_units = #{bonusUnits}");
                }
                if(vsjStageSchema.getPeersNum() != null){
                    SET("peers_num = #{peersNum}");
                }
                if(vsjStageSchema.getPeersHierarchy() != null){
                    SET("peers_hierarchy = #{peersHierarchy}");
                }
                if(vsjStageSchema.getPerrsUnits() != null){
                    SET("perrs_units = #{perrsUnits}");
                }
                if(vsjStageSchema.getProductId() != null){
                    SET("product_id = #{productId}");
                }
                WHERE("id = #{id} and platform_code = #{platformCode}");
            }}.toString();
            return sql;
        }
        
        public String selectStageSchema(@Param("productId")Integer productId,@Param("platformCode")Integer platformCode){
            String sql = new SQL(){{
                SELECT("l.level_name,s.id,s.open_peers,s.bonus_num,s.bonus_units,s.peers_num,s.peers_hierarchy");
                SELECT("s.perrs_units,s.product_id,s.platform_code,l.id AS levelId");
                FROM("vsj_member_level l");
                LEFT_OUTER_JOIN("vsj_stage_schema s ON l.id = s.level_id WHERE " +
                        "s.product_id = #{productId} AND l.platform_code = #{platformCode} UNION ALL " +
                        "SELECT l.level_name,l.id,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,l.id FROM vsj_member_level l " +
                        "WHERE l.id NOT IN ( SELECT s.level_id FROM vsj_stage_schema s WHERE s.product_id = #{productId} " +
                        "AND s.platform_code = #{platformCode} )");
            }}.toString();
            return sql;
        }
        
        public String selectHighLevelAndSchema(@Param("levelId")Integer levelId,@Param("productId")Integer productId,@Param("platformCode")Integer platformCode){
            String sql = new SQL(){{
                SELECT(baseColumn+",ml.sort,ml.level_name,ml.is_default");
                FROM(tableName);
                LEFT_OUTER_JOIN("vsj_member_level ml on ss.level_id = ml.id");
                WHERE("ss.platform_code = #{platformCode} and ss.product_id = #{productId} and ml.sort >= "
                    		+ "( select sort from "
                    		+ " vsj_member_level "
                    		+ " where "
                    		+ " id = #{levelId} )"
                    		);
            }}.toString();
            return sql;
        }

        public String insertStageSchemaBatch(List<VsjStageSchema> list){
            StringBuilder sb = new StringBuilder();
            sb.append("REPLACE INTO vsj_stage_schema (level_id,open_peers,bonus_num,bonus_units,peers_num,peers_hierarchy," +
                    "perrs_units,product_id,platform_code) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].levelId}, #'{'list[{0}].openPeers}, #'{'list[{0}].bonusNum},#'{'list[{0}].bonusUnits}," +
                            "#'{'list[{0}].peersNum},#'{'list[{0}].peersHierarchy},#'{'list[{0}].perrsUnits}," +
                            "#'{'list[{0}].productId},#'{'list[{0}].platformCode})");
            for (int i = 0; i < list.size(); i++) {
                sb.append(mf.format(new Object[] { i }));
                if (i < list.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }
        
    }
}
