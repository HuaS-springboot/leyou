package com.vsj.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname TreeUtil
 * @Description List2Tree
 * @Date 2019/8/22 17:24
 * @Created by wangzx
 */
public class TreeUtils {


    /**
     * @Description ListStr转Tree
     * @Author  wangzx
     * @Date   2019/8/22 18:01
     * @Param  [listStr, id, pid, children]
     * listStr:集合Json
     * id:自己id名称
     * parentId:父级id名称
     * children:子类集合
     * @Return      java.lang.String
     * @Exception
     */
    public static String listToTree(String listStr, String id, String pid, String children) {
        JSONArray arr = JSONArray.parseArray(listStr);
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            //在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVP != null) {
                //检查是否有child属性
                if (hashVP.get(children) != null) {
                    JSONArray ch = (JSONArray) hashVP.get(children);
                    ch.add(aVal);
                    hashVP.put(children, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(children, ch);
                }
            } else {
                r.add(aVal);
            }
        }
        return JSON.toJSONString(r);
    }

    /**
     * demo
     * @param args
     */
    public static void main(String[] args) {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("parentId", 0);
        map.put("name", "甘肃省");
        data.add(map);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("parentId", 1);
        map2.put("name", "天水市");
        data.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        map3.put("parentId", 2);
        map3.put("name", "秦州区");
        data.add(map3);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", 4);
        map4.put("parentId", 0);
        map4.put("name", "北京市");
        data.add(map4);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("id", 5);
        map5.put("parentId", 4);
        map5.put("name", "昌平区");
        data.add(map5);
        System.out.println("转换前JSON:"+JSON.toJSONString(data));
        String tree = listToTree(JSON.toJSONString(data), "id", "parentId", "children");
        System.out.println("转换后JSON:"+tree);
    }
}
