package com.vsj.common.service;

import com.vsj.common.model.KdniaoResponseParam;

public interface IKdniaoTrackQueryService {
	KdniaoResponseParam queryTrackInfo(String expCode, String expNo,int platformCode);
}
