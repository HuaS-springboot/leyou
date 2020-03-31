package com.vsj.common.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.vsj.model.VsjSysRole;
import lombok.Data;
@Data
public class UserLoginModel implements Serializable{

	private static final long serialVersionUID = -3188715024142756168L;
	private Integer userId;
	private String userName;
	private String passWord;
	private String nickName;
	private String token;
	private Map<String,Map<String,String>> sysDictionary;
	private String shopName;
    private Set<VsjSysRole> roles;     // 角色
}

