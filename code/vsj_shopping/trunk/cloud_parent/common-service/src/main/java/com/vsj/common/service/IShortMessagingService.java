package com.vsj.common.service;

import com.vsj.common.model.BaseResponseParam;
@SuppressWarnings("rawtypes")
public interface IShortMessagingService {
	BaseResponseParam sendMessage(String telPhoneNum,byte type,int platformCode);
}
