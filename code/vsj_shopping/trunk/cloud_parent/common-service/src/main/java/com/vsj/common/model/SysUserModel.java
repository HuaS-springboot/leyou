package com.vsj.common.model;

import com.vsj.model.VsjSysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Classname SysUserModel
 * @Description TODO
 * @Date 2019/8/12 10:26
 * @Created by zy
 */
@Data
public class SysUserModel implements Serializable {

    private  Integer id;
    private  String  account;
    private  String  nickName;
    private  String  phone;
    private  Boolean sex;
    private  String  email;
    private  Boolean state;
    private  Set<VsjSysRole> sysRoleModel;
}
