package com.vsj.common.model.request;

import com.vsj.model.VsjSysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Classname SysUserEdit
 * @Description TODO
 * @Date 2019/8/20 14:18
 * @Created by zy
 */
@Data
public class SysUserEdit implements Serializable {

    private Integer id;

    private String account;

    private String nickName;

    private String phone;

    private Boolean sex;

    private String imgUrl;

    private String pswd;

    private String email;

    private Boolean state;

    private Integer createId;

    private String createTime;

    private Integer modifier;

    private String lastModifyTime;

    private String remark;

    private Integer platformCode;

    private Set<VsjSysRole> roles;
}
