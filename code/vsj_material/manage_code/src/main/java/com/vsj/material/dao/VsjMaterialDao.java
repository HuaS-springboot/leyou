package com.vsj.material.dao;

import com.vsj.material.mapper.VsjMaterialMapper;
import com.vsj.material.model.User;
import com.vsj.material.model.VsjCollection;
import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.response.VsjMaterialCollectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import  java.util.List;

/**
 * @Classname VsjMaterialDao
 * @Description 素材管理DAO
 * @Date 2019/8/13 13:50
 * @Created by sxm
 */
@Component
public class VsjMaterialDao {

    @Autowired
    private VsjMaterialMapper  vsjMaterialMapper;

    public List<VsjMaterial> getMaterialList(QueryStat queryStat){
        return vsjMaterialMapper.getMaterialList(queryStat);
    }

    public void  updateMaterial(VsjMaterial vsjMaterial){
        vsjMaterialMapper.updateMaterial(vsjMaterial);
    }

    public void deleteMaterial(QueryStat querySet){
        vsjMaterialMapper.deleteMaterial(querySet);
    }

    public int insertMaterial(VsjMaterial vsjMaterial){
        return vsjMaterialMapper.insertMaterial(vsjMaterial);
    }

    public int updateCount(String mid) {
        return vsjMaterialMapper.updateCount(mid);
    }

    public VsjMaterial queryCount(String mid) {
        return vsjMaterialMapper.queryCount(mid);
    }

    public List<VsjCollection> queryLike(String mid, Integer userid) {
        return vsjMaterialMapper.queryLike(mid,userid);
    }

    public int deleteCollectionByid(String mid ,Integer userid ) {
        return vsjMaterialMapper.deleteCollectionByid(mid,userid);
    }

    public int updateNumu(String mid) {
        return vsjMaterialMapper.updateNumu(mid);
    }

    public int insertCollection(String mid, Integer userid, Integer code) {
        return vsjMaterialMapper.insertCollection(mid,userid,code);
    }

    public int updateNumd(String mid) {
        return vsjMaterialMapper.updateNumd(mid);
    }

    public List<VsjMaterialCollectResponse> getMyMaterialList(QueryStat querySet) {
        return vsjMaterialMapper.getMyMaterialList(querySet);
    }


    public List<Integer> getMyCollectIds(Integer useId) {
        return vsjMaterialMapper.getMyCollectIds(useId);
    }

    public List<VsjMaterial> getAudioVideoMaterialList(QueryStat queryStat) {
        return vsjMaterialMapper.getAudioVideoMaterialList(queryStat);
    }
}
