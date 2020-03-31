package com.vsj.material.mapper;

import com.sun.xml.bind.v2.model.core.ID;
import com.vsj.common.utils.StringUtil;
import com.vsj.material.model.VsjCollection;
import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.response.VsjMaterialCollectResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import com.vsj.material.model.request.QueryStat;
import sun.awt.SunHints;

import java.lang.reflect.Type;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname ProductController
 * @Description 素材管理相关接口
 * @Date 2019/8/13 11:30
 * @Created by sxm
 */
@Mapper
public interface VsjMaterialMapper {

    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "getMaterialList")
    List<VsjMaterial> getMaterialList(QueryStat queryStat);

    @UpdateProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "updateMaterial")
    int updateMaterial(VsjMaterial vsjMaterial);

    @DeleteProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "deleteMaterial")
    int deleteMaterial(QueryStat querySet);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "insertMaterial")
    int insertMaterial(VsjMaterial vsjMaterial);

    @UpdateProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "updateCount")
    int updateCount(String mid);

    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "queryCount")
    VsjMaterial queryCount(String mid);

    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "queryLike")
    List<VsjCollection> queryLike(String mid, Integer userid);

    @DeleteProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "deleteCollectionByid")
    int deleteCollectionByid(@Param("mid") String mid ,@Param("uid") Integer uid);

    @UpdateProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "updateNumu")
    int updateNumu(String mid);

    @InsertProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "insertCollection")
    int insertCollection(String mid, Integer userid, Integer code);

    @UpdateProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "updateNumd")
    int updateNumd(String mid);
    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "getMyMaterialList")
    List<VsjMaterialCollectResponse> getMyMaterialList(QueryStat querySet);
    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "getMyCollectIds")
    List<Integer> getMyCollectIds(Integer useId);
    @SelectProvider(type = VsjMaterialMapper.MaterialProvider.class, method = "getAudioVideoMaterialList")
    List<VsjMaterial> getAudioVideoMaterialList(QueryStat queryStat);


    class MaterialProvider {
        public String getMaterialList(QueryStat queryStat) {
            String sql = new SQL() {
                {
                    SELECT("m.id,m.title,m.content,m.sort,m.collection_num,m.image," +
                            "m.download_num,m.sys_usr_id,m.status,m.one_cate_id,m.two_cate_id," +
                            "m.three_cate_id,m.resource_type,m.platform_code");
                    FROM("vsj_material m");
                    String where = whereBuilder(queryStat);
                    if (StringUtil.isNoEmptyStr(where)) {
                        WHERE(where);
                    }
                    ORDER_BY("m.sort desc");
                }
            }.toString();
            return sql;
        }

        private String whereBuilder(QueryStat queryStat) {
            List<String> list = new ArrayList<>();
            list.add("platform_code = #{platformCode}");
            if (StringUtil.isNoEmptyStr(queryStat.getContent())) {
                list.add("m.content like concat ('%',#{content},'%')");
            }
            if (queryStat.getOneCateId() != null) {
                list.add("m.one_cate_id = #{oneCateId}");
            }
            if (queryStat.getTwoCateId() != null) {
                list.add("m.two_cate_id = #{twoCateId}");
            }
            if(queryStat.getThreeCateId()!=null){
                list.add("m.three_cate_id = #{threeCateId}");
            }
            if(queryStat.getStatus()!=null){
                list.add("m.status = #{status}");
            }
            if(queryStat.getId()!=null){
                list.add("m.id = #{id}");
            }
            if(queryStat.getResourceType()!=null){
                list.add("m.resource_type = #{resourceType}");
            }
            return String.join(" and ", list);
        }

        public String updateMaterial(VsjMaterial vsjMaterial) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material");
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getTitle())) {
                        SET("title = #{title}");
                    }
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getContent())) {
                        SET("content = #{content}");
                    }
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getImage())) {
                        SET("image = #{image}");
                    }
                    if (vsjMaterial.getOneCateId() != null) {
                        SET("one_cate_id = #{oneCateId}");
                    }
                    if (vsjMaterial.getTwoCateId() != null) {
                        SET("two_cate_id = #{twoCateId}");
                    }
                    if (vsjMaterial.getThreeCateId() != null) {
                        SET("three_cate_id = #{threeCateId}");
                    }
                    if (vsjMaterial.getResourceType() !=null){
                        SET("resource_type = #{resourceType}");
                    }
                    if (vsjMaterial.getSort() != null) {
                        SET("sort = #{sort}");
                    }
                    if (vsjMaterial.getDownloadNum() != null) {
                        SET("download_num = #{downloadNum}");
                    }
                    if (vsjMaterial.getCollectionNum() != null) {
                        SET("collection_num = #{collectionNum}");
                    }
                    if (vsjMaterial.getStatus() != null) {
                        SET("status = #{status}");
                    }

                    if (vsjMaterial.getSysUsrId() != null) {
                        SET("sys_usr_id = #{sysUsrId}");
                    }
                    WHERE("id = #{id} and platform_code = #{platformCode}");
                }
            }.toString();
            return sql;
        }

        public String deleteMaterial(QueryStat querySet) {
            String sql = new SQL() {
                {
                    DELETE_FROM("vsj_material");
                    WHERE("FIND_IN_SET(id,#{ids}) and platform_code = #{platformCode}");
                }
            }.toString();
            return sql;
        }

        public String insertMaterial(VsjMaterial vsjMaterial) {
            String sql = new SQL() {
                {
                    INSERT_INTO("vsj_material");
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getTitle())) {
                        VALUES("title", "#{title}");
                    }
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getImage())) {
                        VALUES("image", "#{image}");
                    }
                    if (StringUtil.isNoEmptyStr(vsjMaterial.getContent())) {
                        VALUES("content", "#{content}");
                    }
                    if (vsjMaterial.getSort() != null) {
                        VALUES("sort", "#{sort}");
                    }
                    if (vsjMaterial.getSysUsrId() != null) {
                        VALUES("sys_usr_id", "#{sysUsrId}");
                    }
                    if (vsjMaterial.getCollectionNum() != null) {
                        VALUES("collection_num", "#{collectionNum}");
                    }
                    if (vsjMaterial.getDownloadNum() != null) {
                        VALUES("download_num", "#{downloadNum}");
                    }
                    if (vsjMaterial.getStatus() != null) {
                        VALUES("status", "#{status}");
                    }
                    if (vsjMaterial.getOneCateId() != null) {
                        VALUES("one_cate_id", "#{oneCateId}");
                    }
                    if (vsjMaterial.getTwoCateId() != null) {
                        VALUES("two_cate_id", "#{twoCateId}");
                    }
                    if (vsjMaterial.getThreeCateId() != null) {
                        VALUES("three_cate_id", "#{threeCateId}");
                    }
                    if (vsjMaterial.getResourceType() != null) {
                        VALUES("resource_type", "#{resourceType}");
                    }
                    VALUES("platform_code", "#{platformCode}");
                }
            }.toString();
            return sql;
        }

        public String updateCount(String mid) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material");
                    SET("download_num=download_num+1");
                    WHERE("id = #{mid} ");
                }
            }.toString();
            return sql;
        }

        public String queryCount(String mid) {
            String sql = new SQL() {
                {
                    SELECT("m.id,m.title,m.content,m.sort,m.collection_num," +
                            "m.download_num,m.sys_usr_id,m.status,m.one_cate_id,m.two_cate_id," +
                            "m.three_cate_id,m.platform_code");
                    FROM("vsj_material m");
                    WHERE("id=#{mid}");
                }
            }.toString();
            return sql;
        }

        public String queryLike(String mid, Integer userid) {
            String sql = new SQL() {
                {
                    SELECT("v.id,v.m_id,v.r_id,v.platform_code");
                    FROM("vsj_collection v");
                    WHERE(" m_id = #{mid} and r_id = #{userid}");
                }
            }.toString();
            return sql;
        }

        public String deleteCollectionByid(String mid, Integer uid) {
            String sql = new SQL() {
                {
                    DELETE_FROM("vsj_collection");
                    WHERE("m_id=#{mid} and r_id=#{uid}");
                }
            }.toString();
            return sql;
        }

        public String updateNumu(String mid) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material");
                    SET("collection_num=collection_num-1");
                    WHERE("id = #{mid} ");
                }
            }.toString();
            return sql;
        }

        public String insertCollection(String mid, Integer userid, Integer code) {
            String sql = new SQL() {
                {
                    INSERT_INTO("vsj_collection");

                    VALUES("m_id", "#{mid}");

                    VALUES("r_id", "#{userid}");
                    VALUES("platform_code", "#{code}");
                }
            }.toString();
            return sql;
        }

        public String updateNumd(String mid) {
            String sql = new SQL() {
                {
                    UPDATE("vsj_material");
                    SET("collection_num=collection_num+1");
                    WHERE("id = #{mid} ");
                }
            }.toString();
            return sql;
        }

        public String getMyMaterialList(QueryStat querySet) {
            String sql = new SQL() {
                {
                    SELECT("m.id,m.title,m.content,m.sort,m.collection_num," +
                            "m.download_num,m.sys_usr_id,m.status,m.one_cate_id,m.two_cate_id," +
                            "m.three_cate_id,m.platform_code");

                    FROM("vsj_material m ");
                    if(querySet.getIsMylike() != null){
                        INNER_JOIN("vsj_collection c ON  m.id=c.m_id ");
                    }
                    String where = whereBuilder1(querySet);
                    if (StringUtil.isNoEmptyStr(where)) {
                        WHERE(where);
                    }
                    ORDER_BY("m.sort desc");
                }

            }.toString();
            return sql;
        }

        private String whereBuilder1(QueryStat querySet) {
            List<String> list = new ArrayList<>();
            list.add("m.status=1");
            if (querySet.getUseId()!= null && querySet.getIsMylike()!=null  ){
                list.add("c.r_id = #{useId}");
            }

            if (StringUtil.isNoEmptyStr(querySet.getContent())) {
                list.add("m.content like concat ('%',#{content},'%')");
            }
            if (querySet.getType() != null) {
                list.add("m.one_cate_id = #{type}");
            }
            return String.join(" and ", list);
        }
        public String getMyCollectIds(Integer userId){
            String sql = new SQL() {
                {
                    SELECT("m_id");
                    FROM("vsj_collection");
                    WHERE(" r_id = #{userId}");
                }
            }.toString();
            return sql;
        }
        public String getAudioVideoMaterialList(QueryStat queryStat) {
            String sql = new SQL() {
                {
                    SELECT("m.id,m.title,m.content,m.sort,m.collection_num,m.image," +
                            "m.download_num,m.sys_usr_id,m.status,m.one_cate_id,m.two_cate_id," +
                            "m.three_cate_id,m.platform_code,m.resource_type,l.nick_name");
                    FROM("vsj_material m");
                    LEFT_OUTER_JOIN("vsj_material_register l ON l.id = m.sys_usr_id");
                    String where = whereBuilder3(queryStat);
                    if (StringUtil.isNoEmptyStr(where)) {
                        WHERE(where);
                    }
//                    ORDER_BY("m.sort desc");
                }
            }.toString();
            return sql;
        }
        private String whereBuilder3(QueryStat queryStat) {
            List<String> list = new ArrayList<>();
            list.add("m.platform_code = #{platformCode}");
            list.add("m.resource_type in (2,3)");
            if (StringUtil.isNoEmptyStr(queryStat.getTitle())) {
                list.add("m.title like concat ('%',#{title},'%')");
            }
            if (queryStat.getOneCateId() != null) {
                list.add("m.one_cate_id = #{oneCateId}");
            }
            if (queryStat.getTwoCateId() != null) {
                list.add("m.two_cate_id = #{twoCateId}");
            }
            if(queryStat.getThreeCateId()!=null){
                list.add("m.three_cate_id = #{threeCateId}");
            }
            if(queryStat.getResourceType()!=null){
                list.add("m.resource_type = #{resourceType}");
            }
            if(queryStat.getId()!=null){
                list.add("m.id = #{id}");
            }
            return String.join(" and ", list);
        }
    }

}
