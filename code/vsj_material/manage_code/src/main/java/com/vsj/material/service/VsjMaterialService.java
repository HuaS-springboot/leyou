package com.vsj.material.service;


import com.vsj.material.model.VsjMaterial;
import com.vsj.material.model.request.QueryStat;
import com.vsj.material.model.request.VsjMaterialRequest;
import com.vsj.material.model.request.VsjMaterialRequestList;


public interface VsjMaterialService {

    Object getMaterialList(QueryStat queryStat);

    Object updateMaterials(VsjMaterialRequestList vsjMaterialRequestList,Integer platformCode);

    Object deleteMaterial(QueryStat querySet);

    Object insertMaterial(VsjMaterialRequest vsjMaterialRequest, Integer platformCode,Integer userId);

    Object keepCountingt(String mid);

    Object addLike(String mid, Integer userid, Integer code ,Long type);

    Object getMyMaterialList(QueryStat querySet);

    Object getAudioVideoMaterialList(QueryStat queryStat);
}
