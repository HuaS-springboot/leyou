package com.vsj.common.model.request;

import com.vsj.model.VsjSysPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Classname RoleRequest
 * @Description TODO
 * @Date 2019/8/22 15:15
 * @Created by zy
 */
@Data
public class RoleRequest implements Serializable {

    private Integer id;

    private String roleCode;

    private String roleName;

    private Integer state;

    private Integer createId;

    private String createTime;

    private Integer platformCode;

    private Set<VsjSysPermission> permissions;
}
