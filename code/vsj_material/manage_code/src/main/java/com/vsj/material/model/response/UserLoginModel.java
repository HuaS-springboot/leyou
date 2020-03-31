package com.vsj.material.model.response;


import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Data
public class UserLoginModel implements Serializable {

	private static final long serialVersionUID = -3188715024142756168L;
	private Integer id;
	private String userName;
	private String pswd;
	private String nickName;
	private Integer sex;
	private String token;
	private Map<String, Map<String,String>> sysDictionary;

}